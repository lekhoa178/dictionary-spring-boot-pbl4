
$('#form-account').submit(function (e) {
    alert("in")
    e.preventDefault();
    $.ajax({
        type: 'POST',
        url: '/admin/account/save',
        data: $(this).serialize(),
        success: function(response) {
            alert("success")
            $(fragmentContainer).html(response)
        },
        error: function(error) {
            alert(this.url)
        }
    })
})
