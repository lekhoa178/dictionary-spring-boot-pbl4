const menu = document.querySelector(".menu");
const tabs = menu.querySelectorAll('.menu--tabs');
const fragmentContainer = document.querySelector('.content-container');
const loadedScript = new Set();

function loadScript(url) {
    if (loadedScript.has(url))
        return true
    return false
}
async function init() {
    const fragment = window.location.pathname.substring(1).split('/')[1];
    console.log(fragment)

    const fragmentEl = document.getElementById(`fragment-${fragment}`).closest('.menu--tabs');
    for (let i = 0; i < tabs.length; ++i) {
        tabs[i].classList.remove('menu--tab-active');
    }
    fragmentEl.classList.add('menu--tab-active');

    fragmentContainer.innerHTML = await AJAX(`/admin/${fragment}`);

    const script = document.createElement('script');
    const text = document.createTextNode(await AJAX(`/javascript/fragments_admin/${fragment}.js`));
    script.appendChild(text);

    loadedScript.add(`/javascript/fragments_admin/${fragment}.js`)

    fragmentContainer.append(script);
}

init();

menu.addEventListener('click', async e => {
    e.preventDefault();

    const target = e.target.closest('.menu--tabs');
    if (target.classList.contains('.menu--tab-active')) return;

    for (let i = 0; i < tabs.length; ++i) {
        tabs[i].classList.remove('menu--tab-active');
    }
    target.classList.add('menu--tab-active');

    const fragment = target.id.replace("fragment-", "");
    history.pushState(history.state, document.title, `/admin/${fragment}`);
    fragmentContainer.innerHTML = await AJAX(`/admin/${fragment}`);

    if (loadScript(`/javascript/fragments_admin/${fragment}.js`))
        return

    console.log("execute")

    const script = document.createElement('script');
    const text = document.createTextNode(await AJAX(`/javascript/fragments_admin/${fragment}.js`));
    script.appendChild(text);
    loadedScript.add(`/javascript/fragments_admin/${fragment}.js`)
    fragmentContainer.append(script);
})

// ----------------- UTILITY --------------------

async function AJAX(fragment, json = false) {
    var token = sessionStorage.getItem('jwtToken');
    if (token) {
        $.ajaxSetup({
            headers: {
                Authorization: 'Bearer ' + token,
            },
        });
    }
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