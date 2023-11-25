function buyHearts() {
    $.ajax({
        type: 'POST',
        url: '/store/heart',
        success: function (response) {
            // $("body").html(response)
            location.reload()
        },
        error: function (error) {
            alert(this.url)
        }
    });
}