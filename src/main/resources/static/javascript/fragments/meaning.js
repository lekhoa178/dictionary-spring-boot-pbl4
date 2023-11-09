function speakText(text) {
    const speech = new SpeechSynthesisUtterance(text);
    speech.voice = window.speechSynthesis.getVoices()[0];
    window.speechSynthesis.speak(speech);
}

fragmentContainer.addEventListener('click', async function(e) {
    if (e.target.classList.contains('volume--icon')) {
        if ('speechSynthesis' in window && 'SpeechSynthesisUtterance' in window) {
            const wordEl = document.querySelector('.word');
            speakText(wordEl.innerHTML);
        }
    } else if (e.target.classList.contains('synonym-item')) {

        const word = e.target.innerText;
        console.log(e.target);

        history.pushState(history.state, document.title, `/meaning/${word}`);
        fragmentContainer.innerHTML = await AJAX(`/meaning/${word}`, false);
    }
});