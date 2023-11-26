const returnBtn = document.querySelector('.return--btn');
const cardContainer = document.querySelector('.game--cards');
const answerContainer = document.querySelector('.answer--panel');
const bottomContainer = document.querySelector('.bottom-container');
const gameSentence = document.querySelector('.game--sentence');
const passBtn = document.querySelector('.btn--pass');
const checkBtn = document.querySelector('.btn--check');
const volumeBtn = document.querySelector('.volume--icon');
const requestTitleEl = document.querySelector('.request--title');

const progressEl = document.querySelector('.progress');

let questions = [];

let orViSentence = "";
let correctAns = 0;
let progress = 0;
let totalQuestion = 13;
let heart = document.querySelector('.resource--items-text-heart');
let type = 0;

const bodyEl = document.querySelector('.base-container');
const stageId = bodyEl.dataset.stage;
const levelId = bodyEl.dataset.level;

setupRound();

returnBtn.addEventListener('click', e => {
    window.location = '/learn';
})

cardContainer.addEventListener('click', e => {

    if (!e.target.classList.contains('game--card')) return;

    const cardEl = e.target;

    cardContainer.removeChild(cardEl);
    answerContainer.appendChild(cardEl);

    checkBtn.classList.remove('btn--check--inactive');
})

answerContainer.addEventListener('click', e => {
    if (!e.target.classList.contains('game--card')) return;

    const cardEl = e.target;

    answerContainer.removeChild(cardEl);
    cardContainer.appendChild(cardEl);

    if (!answerContainer.children.length) {
        checkBtn.classList.add('btn--check--inactive');
    }
})

checkBtn.addEventListener('click',  async function(e) {
    if (e.target.classList.contains('btn--check--inactive')) return;

    let ansSentence = "";

    for (const child of answerContainer.children) {
        ansSentence += " " + child.innerText;
    }

    let noticeHTML = "";
    if (orViSentence === ansSentence.substring(1)) {
        correctAns++;

        noticeHTML = `
            <div class="correct-container default-container overlay-container">
                <div style="display: flex; flex-direction: row; gap: 20px; align-items: center">
                    <i class="fa-solid fa-circle-check correct-icon"></i>
                    <p style="font-weight: bold; color: #57b846; font-size: 22px">TUYỆT VỜI</p>
                </div>
                <button class="btn btn--continue btn--correct">TIẾP TỤC</button>
            </div>`;
    }
    else {
        $.ajax({
            type: 'POST',
            url: '/lesson/lostHeart',
            success: function (response) {
                console.log(response)
                if (Number(response) === 0)
                    window.location = '/learn'

                heart.textContent = response
            },
            error: function (error) {
                alert(this.url)
            }
        });



        noticeHTML = `
            <div class="incorrect-container default-container overlay-container">
                <div style="display: grid; grid-template-columns: 1fr 1fr; grid-gap: 5px">
                    <i class="fa-solid fa-circle-xmark incorrect-icon"></i>
                    <p style="color: #FF4B4B; font-weight: bold; font-size: 22px" class="correct-answer">ĐÁP ÁN ĐÚNG:</p>
                    <p style="color: #FF4B4B" class="correct-answer">${orViSentence}</p>
                </div>
                <button class="btn btn--continue btn--incorrect">TIẾP TỤC</button>
            </div>`
    }

    bottomContainer.insertAdjacentHTML("beforeend", noticeHTML);
})

bottomContainer.addEventListener('click', async function(e) {
    if (!e.target.classList.contains('btn--continue')) return;

    bottomContainer.removeChild(bottomContainer.querySelector('.overlay-container'));

    progress++;
    if (progress < totalQuestion) {
        nextQuestion();
    }
    else {
        if (heart > 0) {
            document.querySelector('.base-container').innerHTML
                = await AJAX(`/lesson/finish/${stageId}/${levelId}/${correctAns} ${correctAns / totalQuestion * 100}`);
        }
    }
})

document.addEventListener('click', async function(e) {
    if (!e.target.classList.contains('btn--end')) return;

    window.location.replace('/learn');
    checkBtn.classList.add('btn--check--inactive');
});

const nextQuestion = function() {

    progressEl.style.width = `${100 * progress/totalQuestion}%`;
    answerContainer.innerText = "";

    type = Math.round(Math.random() * 2);

    let randomQues = 0;
    while (randomQues === progress) {
        randomQues = Math.floor(Math.random() * totalQuestion);
    }
    let [ranEnSentence, ranViSentence] = questions[randomQues].split("/");
    let [enSentence, viSentence] = questions[progress].split("/");

    let words;

    gameSentence.innerText = enSentence;
    if (type >= 1) {

        gameSentence.style.display = "block";
        requestTitleEl.innerText = "Viết lại câu bằng tiếng Việt"
        orViSentence = viSentence;

        words = viSentence.replaceAll(" , ", " ").split(" ");

        for (let i = 0; i < words.length; ++i) {
            if (ranViSentence.indexOf(` ${words[i]} `) !== -1) {
                ranViSentence = ranViSentence.replace(words[i], '');
            }
        }

        const rWords = ranViSentence.replaceAll(' , ', ' ').split(' ').filter(word => word !== '')
            .slice(0, Math.min(3, ranViSentence.length));
        words = sortRandomly([...words, ...rWords]);
    }
    else {
        gameSentence.style.display = "none";
        requestTitleEl.innerText = "Nghe và viết lại câu tiếng Anh"

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

        const rWords = ranEnSentence.replaceAll(' , ', ' ').split(' ').filter(word => word !== '')
            .slice(0, Math.min(3, ranEnSentence.length));
        words = sortRandomly([...words, ...rWords]);
    }

    let cardHTML = "";
    for (let i = 0; i < words.length; ++i) {
        cardHTML += `
                <div class="game--card">
                    ${words[i]}
                </div>`;
    }

    cardContainer.innerHTML = cardHTML;
}

async function setupRound() {
    questions = await AJAX(`/cfg/sentences/${stageId}/${levelId}/${totalQuestion}`, true);

    $.ajax({
        type: 'GET',
        url: '/lesson/heart',
        dataType: "json",
        success: function (response) {
            heart.textContent = response
        },
        error: function (error) {
            alert(this.url)
        }
    });
    nextQuestion();
}

function sortRandomly(words) {
    for (let i = words.length - 1; i > 0; i--) {
        let j = Math.floor (Math.random () * (i + 1)); // random index from 0 to i
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

volumeBtn.addEventListener('click', e => {
    if ('speechSynthesis' in window && 'SpeechSynthesisUtterance' in window) {
        speakText(gameSentence.innerText);
    }
})

// ----------------- UTILITY --------------------

async function AJAX(fragment, json = false) {

    try {
        const response = await fetch(fragment, {
            method: 'GET',
            headers: {
                'request-source': 'JS',
            },
        })

        const data = json ? await response.json() : await response.text();

        if (!response.ok) throw new Error("Error response");
        return data;
    }
    catch (e) {
        console.error(e.message);
    }
}