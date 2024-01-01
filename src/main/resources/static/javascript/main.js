const menu = document.querySelector('.menu');
const tabs = menu.querySelectorAll('.menu--tabs');
const fragmentContainer = document.querySelector('.content-container');

let nameValue;
let genderValue;
let birthDateValue;
let emailValue;
const accountId = document.getElementById('metadata').dataset.accountId;
const missionBtn = document.querySelector('.mission--header-detail');

// const token = document.querySelector("#show-token").textContent;
// document.cookie = "jwtToken=" + token + "; path=/";
//
// console.log("Get token from main: ",token);

let loadedScript = new Set();

async function init() {
  const fragment = window.location.pathname.substring(1);
  const baseFragment = fragment.split('/')[0];

  try {
    const fragmentEl = document.getElementById(`fragment-${fragment}`).closest('.menu--tabs');
    for (let i = 0; i < tabs.length; ++i) {
      tabs[i].classList.remove('menu--tab-active');
    }
    fragmentEl.classList.add('menu--tab-active');
  }
  catch (e) {}

  console.log(baseFragment);
  [fragmentContainer.innerHTML] = await Promise.all([AJAX(`/${fragment}`)]);

  const path = `/javascript/fragments/${baseFragment}.js`;
  if (loadedScript.has(path))
    return;

    const script = document.createElement('script');
    const text = document.createTextNode(await AJAX(path));
    script.appendChild(text);
    fragmentContainer.append(script);

    loadedScript.add(path);
}

init();

menu.addEventListener('click', async function (e) {
    e.preventDefault();

    const target = e.target.closest('.menu--tabs');
    if (target == null) return;

    let fragment = target.id.replace('fragment-', '');
    if (fragment === 'more') {
        const subTarget = e.target.closest('.menu--option--item');
        if (subTarget == null)
            return;

        fragment = subTarget.id.replace('fragment-', '');

        if (fragment === "logout") {
            window.location = "/public/logout";
            return;
        }
    }
    const baseFragment = fragment.split('/')[0];

    if (target.classList.contains('.menu--tab-active')) return;

    for (let i = 0; i < tabs.length; ++i) {
        tabs[i].classList.remove('menu--tab-active');
    }
    target.classList.add('menu--tab-active');

    history.pushState(history.state, document.title, `/${fragment}`);
    fragmentContainer.innerHTML = await AJAX(`/${fragment}`);

    const path = `/javascript/fragments/${baseFragment}.js`;
    if (loadedScript.has(path)) {
        if (fragment === 'profile') {
            const script = document.createElement('script');
            const text = document.createTextNode(await AJAX(path));
            script.appendChild(text);
            fragmentContainer.append(script);
        }
        return;
    }

    const script = document.createElement('script');
    const text = document.createTextNode(await AJAX(path));
    script.appendChild(text);
    fragmentContainer.append(script);

    loadedScript.add(path);

});

missionBtn.addEventListener('click', async function(e) {
  e.preventDefault();
  const fragment = "mission";
  const fragmentEl = document.getElementById(`fragment-${fragment}`).closest('.menu--tabs');
  for (let i = 0; i < tabs.length; ++i) {
    tabs[i].classList.remove('menu--tab-active');
  }
  fragmentEl.classList.add('menu--tab-active');

  [fragmentContainer.innerHTML] = await Promise.all([AJAX(`/${fragment}`)]);
  const path = `/javascript/fragments/${fragment}.js`;
  if (loadedScript.has(path))
    return;

  const script = document.createElement('script');
  const text = document.createTextNode(await AJAX(path));
  script.appendChild(text);
  fragmentContainer.append(script);

  loadedScript.add(path);
});

// ------------------- SEARCH ----------------
const searchForm = document.querySelector('.search--form');
const searchBar = document.querySelector('.search--bar');
const searchResults = document.querySelector('.search--results');

let searchData = [];
let searchSelected = 0;
document.addEventListener('click', (e) => {
    if (e.target.closest('.search--form') == null)
        searchResults.classList.add('hidden');
});



async function AJAX(fragment, json = false) {
    // var token = sessionStorage.getItem('jwtToken');
    // if (token) {
    //   $.ajaxSetup({
    //     headers: {
    //       Authorization: 'Bearer ' + token,
    //     },
    //   });
    // }
    // console.log('Token: ', token);

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
// ----------------- UTILITY --------------------
searchResults.addEventListener('click', async function (e) {
    const el = e.target.closest('.search--result');

    if (el == null) return;

    const word = e.target.innerText.replaceAll(' ', '_');
    searchBar.value = e.target.innerText;

    history.pushState(history.state, document.title, `/meaning/${word}`);
    fragmentContainer.innerHTML = await AJAX(`/meaning/${word}`, false);

    const path = `/javascript/fragments/meaning.js`;
    if (loadedScript.has(path)) {
        return;
    }

    const script = document.createElement('script');
    const text = document.createTextNode(await AJAX(path));
    script.appendChild(text);
    fragmentContainer.append(script);

    searchBar.blur();
    searchResults.classList.add('hidden');
});
searchForm.addEventListener('submit', async function (e) {
    e.preventDefault();

    if (searchBar.value.length === 0) return;

    let word;
    if (searchData.length !== 0) {
        searchBar.value = searchData[searchSelected];
        word = searchData[searchSelected].replace(' ', '_');
    } else word = searchBar.value.replaceAll(' ', '_');

    history.pushState(history.state, document.title, `/meaning/${word}`);
    fragmentContainer.innerHTML = await AJAX(`/meaning/${word}`, false);

    const path = `/javascript/fragments/meaning.js`;
    if (loadedScript.has(path)) {
        return;
    }

    const script = document.createElement('script');
    const text = document.createTextNode(await AJAX(path));
    script.appendChild(text);
    fragmentContainer.append(script);

    loadedScript.add(path);

    searchBar.blur();

    searchResults.classList.add('hidden');
});
searchBar.addEventListener('input', async function (e) {
    if (e.target.value) {
        if (searchResults.classList.contains('hidden')) {
            searchResults.classList.remove('hidden');
            searchSelected = 0;
        }

        const word = e.target.value.replaceAll('/', '');
        const data = await AJAX(`/dictionary/search-web/${word}/10`, true);
        searchData = data;

        let results = '';

        for (let i = 0; i < data.length; ++i) {
            results += `
                        <li class="search--result
                            ${i === 0 ? 'search--result--selected' : ''}
                            search--result--${i}">
                            ${data[i].replaceAll('_', ' ')}
                        </li>`;
        }

        if (data.length === 0) {
            results += `
                        <li class="search--result
                            search--result--selected
                            search--result--0">
                            ${e.target.value}
                        </li>`;
        }

        const oldOption = searchResults.querySelector(
            `.search--result--${searchSelected}`
        );
        oldOption?.classList.remove('search--result--selected');
        searchSelected = 0;
        searchResults.innerHTML = results;
    } else {
        searchResults.classList.add('hidden');
    }
});

searchBar.addEventListener('keydown', (event) => {
    const oldOption = searchResults.querySelector(
        `.search--result--${searchSelected}`
    );
    oldOption?.classList.remove('search--result--selected');

    if (event.key === 'ArrowUp') {
        if (searchSelected === 0) searchSelected = searchData.length - 1;
        else searchSelected--;
    } else if (event.key === 'ArrowDown') {
        searchSelected = (searchSelected + 1) % searchData.length;
    }

    const newOption = searchResults.querySelector(
        `.search--result--${searchSelected}`
    );
    newOption?.classList.add('search--result--selected');
});

