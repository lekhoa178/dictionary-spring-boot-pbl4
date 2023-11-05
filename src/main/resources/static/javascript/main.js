const menu = document.querySelector(".menu");
const tabs = menu.querySelectorAll('.menu--tabs');
const fragmentContainer = document.querySelector('.content-container');

function init() {

    const fragment = window.location.pathname.replace('/', '');

    const fragmentEl = document.getElementById(`fragment-${fragment}`);
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
    console.log(fragmentContainer);
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