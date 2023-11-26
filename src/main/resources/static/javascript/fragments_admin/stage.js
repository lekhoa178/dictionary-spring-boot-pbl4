const loadedJs = new Set();
fragmentContainer.addEventListener('click', async function (e) {
    if (e.target.classList.contains('add-stage')) {

        $.ajax({
            type: 'GET',
            url: '/admin/stage/add',
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert(this.url)
            }
        });
    } else if (e.target.closest('.update-stage')) {

        var id = e.target.closest('.update-stage').value;

        $.ajax({
            type: 'GET',
            url: '/admin/stage/update/' + id,
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert(this.url)
            }
        });
    }
    else if(e.target.closest('.open-stage')) {
        e.preventDefault()
        var id = e.target.closest('.open-stage').value;

        $.ajax({
            type: 'GET',
            url: '/admin/level/' + id,
            success: async function (response) {
                $(fragmentContainer).html(response)

                if (loadedJs.has(`/javascript/fragments_admin/level.js`))
                {
                    init()
                    return
                }
                const script = document.createElement('script');
                const text = document.createTextNode(await AJAX(`/javascript/fragments_admin/level.js`));


                script.appendChild(text);
                loadedJs.add(`/javascript/fragments_admin/level.js`)
                fragmentContainer.append(script)
            },
            error: function(error) {
                alert(this.url)
            }
        });
    }
})