fragmentContainer.addEventListener('click', async function (e) {
    if (e.target.closest('.update-mission')) {

        var missionId = e.target.closest('.update-mission').value;

        $.ajax({
            type: 'GET',
            url: '/admin/mission/update/' + missionId,
            data: {},
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert('fail')
            }
        });
    }
    else if (e.target.closest('.add-mission')) {
        $.ajax({
            type: 'GET',
            url: '/admin/mission/add',
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert('fail')
            }
        });
    }
    else if (e.target.closest('.submit-mission')) {
        let missionForm = document.getElementById('form-mission')

        let mission = {
            id: document.getElementById('id-txt').value,
            name: document.getElementById('name-txt').value,
            description: document.getElementById('description-txt').value,
            point: document.getElementById('point-txt').value,
            type: document.getElementById('type-select').value,
            target: document.getElementById('target-txt').value
        }

        $.ajax({
            type: 'POST',
            url: '/admin/mission/save',
            data: mission,
            success: function (response) {
                $(fragmentContainer).html(response)
            },
            error: function (error) {
                alert('fail')
            }
        });
    }
})
