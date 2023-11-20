let vocabulary_container = document.querySelector('.vocabulary-field')
let level_container = document.querySelector('.level-container')

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

fragmentContainer.addEventListener('click',async function(e){
    if(e.target.classList.contains("add-level")) {

        var lastRow = document.querySelector('.level-list tbody tr:last-child');
        var stageLabel = document.querySelector('.stage-num')

        var stage = stageLabel.textContent.split(' ')[1];

        var level
        if (lastRow == null)
            level = 0
        else
            level = lastRow.textContent.trim().split(' ')[1];

        $.ajax({
            type: 'POST',
            url: '/admin/level/add?stageId=' + stage + '&levelId=' + level,
            success: function(response) {
                $(level_container).html(response)
            },
            error: function(error) {
                alert(this.url)
            }
        });
    }
})

// function addRow() {
//     // Get the table body
//     var tableBody = document.getElementById('vocab-table').getElementsByTagName('tbody')[0];
//
//     // Clone the template row (assuming the first row is your template)
//     var newRow = tableBody.rows[0].cloneNode(true);
//
//     // Clear input values in the new row
//     clearInputValues(newRow);
//
//     // Append the new row to the table
//     tableBody.appendChild(newRow);
// }

function removeRow(rowId) {
    // Get the table body
    var tableBody = document.getElementById('vocab-table').getElementsByTagName('tbody')[0];

    // Remove the row with the specified id
    var rowToRemove = document.getElementById('row-' + rowId);
    tableBody.removeChild(rowToRemove);
}

function clearInputValues(row) {
    // Clear input values in the specified row
    var inputs = row.getElementsByTagName('td');
    console.log(inputs.length)
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].textContent = '';
    }
}

function sendDataToController() {
    var dataToSend = [];
    const rows = document.querySelectorAll('.rowData')

    let stageId = document.querySelector('.stageId').value
    let levelId = document.querySelector('.levelId').value

    for (let i = 0; i < rows.length; ++i) {
        let rowData = {};

        rowData.id = {
            levelId: {
                stageId: stageId,
                levelId:levelId},
            vocabularyNum: rows[i].querySelector('.vocab-id').value
        };

        rowData.level = {
            id: {
                stageId: stageId,
                levelId: levelId
            }
        }

        rowData.word = rows[i].querySelector('.word-input').value;
        rowData.meaning = rows[i].querySelector('.meaning-input').value;
        rowData.type = rows[i].querySelector('.type-input').value;

        dataToSend.push(rowData);
    }
    console.log(JSON.stringify(dataToSend))
    // Send data to the controller using AJAX
    $.ajax({
        type: 'POST',
        url: '/admin/vocabulary/save',
        contentType: 'application/json',
        data: JSON.stringify(dataToSend),
        success: function(response) {
            // Handle the response from the server
            $(vocabulary_container).html(response)
        },
        error: function(error) {
            // Handle errors
            console.error('Error:', error);
        }
    });
}

vocabulary_container.addEventListener('click',async function(e){
    if(e.target.id === 'vocab-form-btn') {

        let stageId = document.querySelector('.stageId').value
        let levelId = document.querySelector('.levelId').value

        $.ajax({
            type: 'GET',
            url: '/admin/vocabulary/add?stageId=' + stageId + '&levelId='+ levelId,
            success: function(response) {
                $(vocabulary_container).html(response)
            },
            error: function(error) {
                alert(this.url)
            }
        });
    }
})