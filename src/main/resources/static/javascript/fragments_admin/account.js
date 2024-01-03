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

fragmentContainer.addEventListener('click', async function (e) {
    if (e.target.closest('.submit-account')) {
        let accountId = document.getElementById('extra-accountId').value

        $.ajax({
            type: 'GET',
            url: `/dictionary/account/extra/${accountId}`,
            success: function (response) {
                let extra = response;
                let balance= (!(document.getElementById('account-balance').value === "")) ? parseInt(document.getElementById('account-balance').value) : 0;
                console.log(balance)
                let account = {
                    accountId: document.getElementById('account-id-txt').value,
                    username: document.getElementById('user-txt').value,
                    password: document.getElementById('account-pass-txt').value,
                    name: document.getElementById('name-txt').value,
                    birthdate: document.getElementById('date-picker').value,
                    email: document.getElementById('email-txt').value,
                    gender: (document.getElementById('male-btn').checked),
                    enabled: document.getElementById('enable-btn').checked,
                    type: null
                }

                if (document.getElementById('admin-btn').checked)
                    account.type = 4
                else if (document.getElementById('user-btn').checked)
                    account.type = 5

                if (document.getElementById('pass-txt') != null)
                    account.password = document.getElementById('pass-txt').value

                console.log(account)
                $.ajax({
                    type: 'POST',
                    url: '/admin/account/save/' + balance,
                    data: account,
                    success: function (response) {
                        $(fragmentContainer).html(response)
                    },
                    error: function (error) {
                        alert('fail')
                    }
                });
            },
            error: function (error) {
                alert('fail')
            }
        });

    }
})