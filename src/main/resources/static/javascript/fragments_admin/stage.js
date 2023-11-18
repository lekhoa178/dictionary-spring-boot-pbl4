fragmentContainer.addEventListener('click',async function(e){
    if(e.target.classList.contains('add-stage')) {

        $.ajax({
            type: 'GET',
            url: '/admin/stage/add',
            success: function(response) {
                $(fragmentContainer).html(response)
            },
            error: function(error) {
                alert(this.url)
            }
        });
    }
})

fragmentContainer.addEventListener('click',async function(e){
    if(e.target.closest('.update-stage')) {

        var id = e.target.closest('.update-stage').value;

        $.ajax({
            type: 'GET',
            url: '/admin/stage/update/' + id,
            success: function(response) {
                $(fragmentContainer).html(response)
            },
            error: function(error) {
                alert(this.url)
            }
        });
    }
})

fragmentContainer.addEventListener('click',async function(e){
    if(e.target.closest('.add-level')) {

        var id = e.target.closest('.add-level').value;

        $.ajax({
            type: 'GET',
            url: '/admin/level/' + id,
            success: function(response) {
                $(fragmentContainer).html(response)
            },
            error: function(error) {
                alert(this.url)
            }
        });
    }
})