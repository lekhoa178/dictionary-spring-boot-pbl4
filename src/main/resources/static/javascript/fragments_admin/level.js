const vocabulary_container = document.querySelector('.vocabulary-field')

fragmentContainer.addEventListener('click',async function(e){
    if(e.target.classList.contains("level-row")) {

        var data = e.target.getAttribute('data-values').split('_');
        var stage = data[1];
        var level = data[0];
        $.ajax({
            type: 'GET',
            url: '/admin/vocabulary?levelId=' + level + '&stageId=' + stage,
            success: function(response) {
                $(vocabulary_container).html(response)
            },
            error: function(error) {
                alert(this.url)
            }
        });
    }
})