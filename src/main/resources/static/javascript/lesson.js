const returnBtn = document.querySelector('.return--btn');
const cardContainer = document.querySelector('.game--cards');
const answerContainer = document.querySelector('.answer--panel');
const bottomContainer = document.querySelector('.bottom-container');
const gameSentence = document.querySelector('.game--sentence');
const passBtn = document.querySelector('.btn--pass');
const checkBtn = document.querySelector('.btn--check');
const volumeBtn = document.querySelector('.volume--icon');
const requestTitleEl = document.querySelector('.request--title');
const dialog = document.querySelector('.dialog-container');
const dialogExitBtn = document.querySelector('.exit-btn');
const dialogBuyHeartBtn = document.querySelector('.buy-btn');
const progressEl = document.querySelector('.progress');

let stageType = 'learn';

if (dialog == null) console.log('null');
let questions = [];
let questionTypes = [];
let answerResults = [];

let orViSentence = '';
let correctAns = 0;
let progress = 0;
let totalQuestion = 13;
let heart = document.querySelector('.resource--items-text-heart');
let type = 0;

const bodyEl = document.querySelector('.base-container');
const stageId = bodyEl.dataset.stage;
const levelId = bodyEl.dataset.level;
const accountId = bodyEl.dataset.accountId;
const fulfilled = bodyEl.dataset.fulfilled === true ? 1 : 0;

setupRound();

returnBtn.addEventListener('click', (e) => {
  window.location = '/learn';
});

cardContainer.addEventListener('click', (e) => {
  if (!e.target.classList.contains('game--card')) return;

  const cardEl = e.target;

  cardContainer.removeChild(cardEl);
  answerContainer.appendChild(cardEl);

  checkBtn.classList.remove('btn--check--inactive');
});

answerContainer.addEventListener('click', (e) => {
  if (!e.target.classList.contains('game--card')) return;

  const cardEl = e.target;

  answerContainer.removeChild(cardEl);
  cardContainer.appendChild(cardEl);

  if (!answerContainer.children.length) {
    checkBtn.classList.add('btn--check--inactive');
  }
});

checkBtn.addEventListener('click', async function (e) {
  if (e.target.classList.contains('btn--check--inactive')) return;

  let ansSentence = '';

  for (const child of answerContainer.children) {
    ansSentence += ' ' + child.innerText;
  }

  let noticeHTML = '';
  if (orViSentence === ansSentence.substring(1)) {
    answerResults[progress] = true;
    correctAns++;

    noticeHTML = `
            <div class="correct-container default-container overlay-container">
                <div style="display: flex; flex-direction: row; gap: 20px; align-items: center">
                    <i class="fa-solid fa-circle-check correct-icon"></i>
                    <p style="font-weight: bold; color: #57b846; font-size: 22px">TUYỆT VỜI</p>
                </div>
                <button class="btn btn--continue btn--correct">TIẾP TỤC</button>
            </div>`;
  } else {
    $.ajax({
      type: 'POST',
      url: '/lesson/lostHeart',
      success: function (response) {
        if (Number(response) === 0) {
          // window.location = '/learn'
          dialog.style.visibility = 'visible';
        }
        heart.textContent = response;
      },
      error: function (error) {
        alert(this.url);
      },
    });

    noticeHTML = `
            <div class="incorrect-container default-container overlay-container">
                <div style="display: grid; grid-template-columns: 1fr 1fr; grid-gap: 5px">
                    <i class="fa-solid fa-circle-xmark incorrect-icon"></i>
                    <p style="color: #FF4B4B; font-weight: bold; font-size: 22px" class="correct-answer">ĐÁP ÁN ĐÚNG:</p>
                    <p style="color: #FF4B4B" class="correct-answer">${orViSentence}</p>
                </div>
                <button class="btn btn--continue btn--incorrect">TIẾP TỤC</button>
            </div>`;
  }

  bottomContainer.insertAdjacentHTML('beforeend', noticeHTML);
});

bottomContainer.addEventListener('click', async function (e) {
  if (!e.target.classList.contains('btn--continue')) return;

  bottomContainer.removeChild(
    bottomContainer.querySelector('.overlay-container')
  );

  progress++;
  if (progress < totalQuestion) {
    nextQuestion();
  } else {
    if (parseInt(heart.textContent) > 0) {
      console.log(heart.textContent);
      document.querySelector('.base-container').innerHTML = await AJAX(
        `/lesson/finish/${stageId}/${levelId}/${fulfilled}/${correctAns} ${
          (correctAns / totalQuestion) * 100
        }`
      );

      const reviewBodyEl = document.querySelector('.review--body');
      for (let i = 0; i < totalQuestion; ++i) {
        let [enSentence, viSentence] = questions[i].split('/');
        if (enSentence.size > 40) enSentence = enSentence.slice(0, 40) + '...';

        reviewBodyEl.insertAdjacentHTML(
          'beforeend',
          `<div class="review--item" style="background-color: ${
            answerResults[i] ? '#c6ffbb' : '#ffcdd3'
          }"
                        data-index="${i}">
                    <p class="review--item--header">${
                      questionTypes[i] >= 1 ? 'Dịch lại câu' : 'Luyện nghe câu'
                    }</p>
                    <p class="review--item--sentence">${enSentence}</p>
                  </div>`
        );
      }
    }
  }
});

document.addEventListener('click', async function (e) {
  if (e.target.classList.contains('btn--end')) {
    window.location.replace('/learn');
    checkBtn.classList.add('btn--check--inactive');
  } else if (e.target.classList.contains('btn--review')) {
    const panelEl = document.querySelector('.review--overlay');
    panelEl.classList.remove('hidden');
  } else if (e.target.classList.contains('review--collapse-btn')) {
    const panelEl = document.querySelector('.review--overlay');
    panelEl.classList.add('hidden');
  } else if (e.target.closest('.review--item')) {
    const detailEl = document.querySelector('.review--detail--overlay');
    detailEl.classList.remove('hidden');

    const detailConEl = document.querySelector('.review--detail-container');
    const index = e.target.closest('.review--item').dataset.index;
    console.log(index);
    let [enSentence, viSentence] = questions[index].split('/');

    detailConEl.innerHTML = `
              <div class="review--header">
                <div class="review--title">Câu ${parseInt(index) + 1}:</div>
                <button class="review--detail--collapse-btn">X</button>
              </div>
              <p class="review--detail--header">${
                questionTypes[index] >= 1 ? 'Dịch lại câu' : 'Luyện nghe câu'
              }</p>
              <p class="review--detail--sentence">Câu hỏi: ${enSentence}</p>
              <p class="review--detail--sentence">Câu trả lời: ${viSentence}</p>
    `;
  } else if (e.target.classList.contains('review--detail--collapse-btn')) {
    const detailEl = document.querySelector('.review--detail--overlay');
    detailEl.classList.add('hidden');
  }
});

const nextQuestion = function () {
  progressEl.style.width = `${(100 * progress) / totalQuestion}%`;
  answerContainer.innerText = '';

  type = questionTypes[progress];

  let randomQues = 0;
  while (randomQues === progress) {
    randomQues = Math.floor(Math.random() * totalQuestion);
  }
  let [ranEnSentence, ranViSentence] = questions[randomQues].split('/');
  let [enSentence, viSentence] = questions[progress].split('/');

  let words;

  gameSentence.innerText = enSentence;
  if (type >= 1) {
    gameSentence.style.display = 'block';
    requestTitleEl.innerText = 'Viết lại câu bằng tiếng Việt';
    orViSentence = viSentence;

    words = viSentence.replaceAll(' , ', ' ').split(' ');

    for (let i = 0; i < words.length; ++i) {
      if (ranViSentence.indexOf(` ${words[i]} `) !== -1) {
        ranViSentence = ranViSentence.replace(words[i], '');
      }
    }

    const rWords = ranViSentence
      .replaceAll(' , ', ' ')
      .split(' ')
      .filter((word) => word !== '')
      .slice(0, Math.min(3, ranViSentence.length));
    words = sortRandomly([...words, ...rWords]);
  } else {
    gameSentence.style.display = 'none';
    requestTitleEl.innerText = 'Nghe và viết lại câu tiếng Anh';

    enSentence = enSentence.slice(0, -1);
    ranEnSentence = ranEnSentence.slice(0, -1);

    orViSentence = enSentence;

    console.log(enSentence);
    console.log(ranEnSentence);

    words = [...enSentence.replaceAll(' , ', ' ').split(' ')];

    for (let i = 0; i < words.length; ++i) {
      if (ranEnSentence.indexOf(` ${words[i]} `) !== -1) {
        ranEnSentence = ranEnSentence.replace(words[i], '');
      }
    }

    const rWords = ranEnSentence
      .replaceAll(' , ', ' ')
      .split(' ')
      .filter((word) => word !== '')
      .slice(0, Math.min(3, ranEnSentence.length));
    words = sortRandomly([...words, ...rWords]);
  }

  let cardHTML = '';
  for (let i = 0; i < words.length; ++i) {
    cardHTML += `
                <div class="game--card">
                    ${words[i]}
                </div>`;
  }

  cardContainer.innerHTML = cardHTML;
};

async function setupRound() {
  stageType = document.getElementById('stage-metadata').dataset.type;
  if (stageType === 'learn' || stageType === 'listen') {
    questions = await AJAX(
      `/cfg/sentences/${stageId}/${levelId}/${totalQuestion}`,
      true
    );
  } else if (stageType === 'practice') {
    const jsonQuestions = await AJAX(
      `/practice/sentences/${accountId}/${totalQuestion}`,
      true
    );

    console.log(jsonQuestions);

    for (let i = 0; i < jsonQuestions.sentences.length; ++i) {
      questions.push(
        jsonQuestions.sentences[i].English +
          '/' +
          jsonQuestions.sentences[i].Vietnamese
      );
    }

    console.log(questions);
  }

  questionTypes = [];
  if (stageType === 'listen') {
    for (let i = 0; i < totalQuestion; i++) {
      questionTypes.push(0);
    }
  } else {
    for (let i = 0; i < totalQuestion; i++) {
      questionTypes.push(Math.round(Math.random() * 2));
    }
  }

  $.ajax({
    type: 'GET',
    url: '/lesson/heart',
    dataType: 'json',
    success: function (response) {
      heart.textContent = response;
    },
    error: function (error) {
      alert(this.url);
    },
  });
  nextQuestion();
}

function sortRandomly(words) {
  for (let i = words.length - 1; i > 0; i--) {
    let j = Math.floor(Math.random() * (i + 1)); // random index from 0 to i
    const temp = words[i]; // swap list [i] and list [j]
    words[i] = words[j];
    words[j] = temp;
  }

  return words;
}

function speakText(text) {
  const speech = new SpeechSynthesisUtterance(text);
  speech.voice = window.speechSynthesis.getVoices()[0];
  window.speechSynthesis.speak(speech);
}

volumeBtn.addEventListener('click', (e) => {
  if ('speechSynthesis' in window && 'SpeechSynthesisUtterance' in window) {
    speakText(gameSentence.innerText);
  }
});

dialogExitBtn.addEventListener('click', function (e) {
  window.location.replace('/learn');
});

dialogBuyHeartBtn.addEventListener('click', function (e) {
  $.ajax({
    type: 'POST',
    url: '/store/heart',
    success: function (response) {
      console.log(response.balance);
      dialog.style.visibility = 'hidden';
      heart.textContent = response.hearts;

      if (response.balance < 200) {
        if (dialogExitBtn == null) console.log('null');
        dialogBuyHeartBtn.disabled = true;
      }
    },
    error: function (error) {
      console.log(error);
    },
  });
});

// ----------------- UTILITY --------------------

async function AJAX(fragment, json = false) {
  try {
    const response = await fetch(fragment, {
      method: 'GET',
      headers: {
        'request-source': 'JS',
      },
    });

    const data = json ? await response.json() : await response.text();

    if (!response.ok) throw new Error('Error response');
    return data;
  } catch (e) {
    console.error(e.message);
  }
}
