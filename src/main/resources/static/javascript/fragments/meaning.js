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
    else if (e.target.classList.contains('fa-heart')) {
        var fv_icon = document.getElementById('fv-icon')
        var word = document.getElementById('word').value
        var isExist = document.getElementById('isExist').value

        if (fv_icon.classList.contains('fa-solid'))
        {
            fv_icon.classList.remove('fa-solid')
            fv_icon.classList.add('fa-regular')
        }
        else {
            fv_icon.classList.remove('fa-regular')
            fv_icon.classList.add('fa-solid')
        }
        $(document).ready(function(){
            alert('worked');
            // ajax call here
            $.ajax({
                type: 'POST',
                url: '/notebook/update',
                contentType: 'application/json',
                data: JSON.stringify({ word: word, isExist: isExist }),
                success: function(response) {
                    // Handle the response from the server (e.g., show a message)
                    alert("success")
                },
                error: function(error) {
                    // Handle errors
                }
            });
        });

    }
});