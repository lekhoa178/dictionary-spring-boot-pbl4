fragmentContainer.addEventListener('click', async function(e){
    if(e.target.classList.contains('word-card')) {
        var word = e.target.innerText;
        word = word.replaceAll(' ', '_')

        history.pushState(history.state, document.title, `/meaning/${word}`);
        fragmentContainer.innerHTML = await AJAX(`/meaning/${word}`, false);
    }
})