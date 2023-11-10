const menu = document.querySelector(".menu");
const tabs = menu.querySelectorAll('.menu--tabs');
const fragmentContainer = document.querySelector('.content-container');

function init() {
    const path = window.location.pathname

    const parts = path.split('/')
    const fragment = parts[2]
    let pagePath = ''

    // if (parts.length == 5)
    // {
    //     const value = parts[parts.length - 1]
    //     const sub = parts[parts.length - 2]
    //     pagePath = pagePath + `/${sub}/${value}`
    // }
    // else if (parts.length == 4) {
    //     const sub = parts[parts.length - 1]
    //     pagePath = pagePath + `/${sub}`
    // }
    if (parts[3] != null)
        pagePath = pagePath + `/${parts[3]}`
    if (parts[4] != null)
        pagePath = pagePath + `/${parts[4]}`
    // const fragment = "account";
    const fragmentEl = document.getElementById(`fragment-${fragment}`);
    for (let i = 0; i < tabs.length; ++i) {
        tabs[i].classList.remove('menu--tab-active');
    }
    fragmentEl.classList.add('menu--tab-active');
    fetch(`/admin/${fragment}` + pagePath, {
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
    history.pushState(history.state, document.title, `/admin/${fragment}`);
    fetch(`/admin/${fragment}`, {
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

