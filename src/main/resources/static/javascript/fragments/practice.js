fragmentContainer.addEventListener('click', async function(e){
    const wordCardEl = e.target.closest('.word-card');
    if (wordCardEl == null) return;
    console.log("in event")

    const word = wordCardEl.dataset.word.replaceAll(' ', '_');

    history.pushState(history.state, document.title, `/meaning/${word}`);
    fragmentContainer.innerHTML = await AJAX(`/meaning/${word}`, false);
})