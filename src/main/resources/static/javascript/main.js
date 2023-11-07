const menu = document.querySelector(".menu");
const tabs = menu.querySelectorAll('.menu--tabs');
const fragmentContainer = document.querySelector('.content-container');

const searchForm = document.querySelector(".search--form");
const searchBar = document.querySelector(".search--bar");

function init() {

    const fragment = window.location.pathname.substring(1);

    const fragmentEl = document.getElementById(`fragment-learn`);
    for (let i = 0; i < tabs.length; ++i) {
        tabs[i].classList.remove('menu--tab-active');
    }
    fragmentEl.classList.add('menu--tab-active');

    fetch(`/${fragment}`, {
        method: 'GET',
        headers: {
            'request-source': 'JS',
        },
    })
        .then(function(response) {
            return response.text();
        })
        .then(function(data) {
            // Update the fragment content with the fetched data
            fragmentContainer.innerHTML = data;
        })
        .catch(function(error) {
            console.error('Error:', error);
        });
}

init();

menu.addEventListener('click', e => {
    e.preventDefault();

    const target = e.target.closest('.menu--tabs');
    if (target.classList.contains('.menu--tab-active')) return;

    for (let i = 0; i < tabs.length; ++i) {
        tabs[i].classList.remove('menu--tab-active');
    }
    target.classList.add('menu--tab-active');

    const fragment = target.id.replace("fragment-", "");
    history.pushState(history.state, document.title, `/${fragment}`);
    fetch(`/${fragment}`, {
        method: 'GET',
        headers: {
            'request-source': 'JS',
        },
    })
        .then(function(response) {
            return response.text();
        })
        .then(function(data) {
            // Update the fragment content with the fetched data
            fragmentContainer.innerHTML = data;
        })
        .catch(function(error) {
            console.error('Error:', error);
        });

})

searchForm.addEventListener("submit", e => {
    e.preventDefault();

    const word = searchBar.value;
    history.pushState(history.state, document.title, `/meaning/${word}`);
    fetch(`/meaning/${word}`, {
        method: 'GET',
        headers: {
            'request-source': 'JS',
        },
    })
        .then(function(response) {
            return response.text();
        })
        .then(function(data) {
            console.log(data);
            // Update the fragment content with the fetched data
            fragmentContainer.innerHTML = data;
        })
        .catch(function(error) {
            console.error('Error:', error);
        });

})