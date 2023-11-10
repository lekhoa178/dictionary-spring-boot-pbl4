
// let searchForm = document.getElementById("form1")
// if (searchForm!= null)
//     alert("null")
// searchForm.addEventListener("submit", async function(e)  {
//
//     e.preventDefault();
//
//     let word = document.getElementById("txt-search").value
//     alert(word)
//     history.pushState(history.state, document.title, `/admin/account/search/${word}`);
//     if (word != "")
//         fragmentContainer.innerHTML = await AJAX(`/admin/account/search/${word}`, false);
//     else
//         fragmentContainer.innerHTML = await AJAX(`/admin/account`, false);
// })
function updateLink() {
    var textfield = document.getElementById("txt-search");
    var form = document.getElementById("form1")

    if (textfield.value != "") {
        form.action = form.action + '/search/' + textfield.value

    }
}
function deleteMany() {

    if(confirm("Bạn có chắc muốn xóa những tài khoản này ?"))
    {
        document.getElementById("data-form").submit()
        return true
    }
    else
        return false
}