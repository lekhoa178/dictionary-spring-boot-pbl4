fragmentContainer.addEventListener('click', async function (e) {
    const updateBtn = e.target.closest('.action-update')
    if (updateBtn == null)
        return
    console.log("in event")

    var word = e.target.closest('.action-update').value;
    // history.pushState(history.state, document.title, `/admin/account/update/${word}`);
    // fragmentContainer.innerHTML = await AJAX(`/admin/account/update/${word}`, false);

    $.ajax({
        type: 'POST',
        url: '/admin/account/update/' + word,
        data: {},
        success: function (response) {
            $(fragmentContainer).html(response)
        },
        error: function (error) {
            alert('fail')
        }
    });


})

fragmentContainer.addEventListener('click', async function (e) {
    if (e.target.classList.contains('action-delete')) {
        var accountId = e.target.value;
        if (!confirm('Bạn có chắc muốn xóa tài khoản này ?'))
            return
        $.ajax({
            type: 'POST',
            url: '/admin/account/delete',
            data: {
                accountId: accountId
            },
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert(this.url)
            }
        });
    }
})

fragmentContainer.addEventListener('click', async function (e) {

    if (e.target.classList.contains('action-add')) {

        $.ajax({
            type: 'GET',
            url: '/admin/account/add',
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert(this.url)
            }
        });
    }
})

fragmentContainer.addEventListener('click', async function (e) {
    if (e.target.classList.contains('action-delete-many')) {
        if (!confirm('Bạn có chắc muốn xóa những tài khoản này ?'))
            return

        $.ajax({
            type: 'POST',
            url: '/admin/account/deleteMany',
            data:
                $('.checkboxes:checked').serialize(),
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert(this.url)
            }
        });
    }
})

fragmentContainer.addEventListener('click', async function (e) {
    if (e.target.classList.contains('nav-btn')) {

        var pageNum = parseInt(document.getElementById('page-num').textContent)
        var totalPage = parseInt(document.getElementById('total-page').textContent)

        if (pageNum - 1 > 0 && e.target.classList.contains('back-btn'))
            pageNum = pageNum - 1;
        else if (pageNum - 1 < totalPage - 1 && e.target.classList.contains('next-btn'))
            pageNum = pageNum + 1
        else
            return

        $.ajax({
            type: 'GET',
            url: '/admin/account/page/' + (pageNum - 1),
            headers: {
                'request-source':
                    'JS'
            },
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert(this.url)
            }
        });
    }
})

fragmentContainer.addEventListener('click', async function (e) {
    if (e.target.closest('.search-btn')) {
        var textfield = document.getElementById("txt-search");
        var keyword = textfield.value
        var path = ''
        if (keyword !== "") {
            path = '/search/' + keyword
        }

        $.ajax({
            type: 'GET',
            url: '/admin/account' + path,
            headers: {
                'request-source': 'JS'
            },
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert(this.url)
            }
        });
    }
})