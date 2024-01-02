
$('#form-account').submit(function (e) {
    e.preventDefault();
    $.ajax({
        type: 'POST',
        url: '/admin/account/save',
        data: $(this).serialize(),
        success: function(response) {
            $(fragmentContainer).html(response)
        },
        error: function(error) {
            alert(this.url)
        }
    })
})
