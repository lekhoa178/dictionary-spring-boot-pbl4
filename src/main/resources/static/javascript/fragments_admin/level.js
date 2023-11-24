let vocabulary_container = document.querySelector('.vocabulary-field')
let level_container = document.querySelector('.level-container')
let loadedVocabJs = false;
let vocabulary_form = null;

function init() {
    level_container = document.querySelector('.level-container')
    const levels = level_container.querySelectorAll('.level-row');

    for (let i = 0; i < levels.length; ++i) {
        levels[i].classList.remove('level-row-active');
    }
    levels[0].classList.add('level-row-active');

    var data = levels[0].getAttribute('data-values').split('_');
    var stage = data[1];
    var level = data[0];

    $.ajax({
        type: 'GET',
        url: '/admin/vocabulary?levelId=' + level + '&stageId=' + stage,
        success: async function (response) {
            vocabulary_container = document.querySelector('.vocabulary-field')
            $(vocabulary_container).html(response)
        },
        error: function(error) {
            alert(this.url)
        }
    });
}

init()
fragmentContainer.addEventListener('click',async function(e){
    if(e.target.classList.contains("level-row")) {

        var data = e.target.getAttribute('data-values').split('_');
        var stage = data[1];
        var level = data[0];

        level_container = document.querySelector('.level-container')

        let levels = level_container.querySelectorAll('.level-row');
        console.log(levels.length)
        if (e.target.classList.contains('.level-row-active')) return;

        for (let i = 0; i < levels.length; ++i) {
            levels[i].classList.remove('level-row-active');
            console.log("remove")
        }
        e.target.classList.add('level-row-active');

        $.ajax({
            type: 'GET',
            url: '/admin/vocabulary?levelId=' + level + '&stageId=' + stage,
            success: async function (response) {
                vocabulary_container = document.querySelector('.vocabulary-field')
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
                level_container = document.querySelector('.level-container')
                $(level_container).html(response)
            },
            error: function(error) {
                alert(this.url)
            }
        });
    }
})

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
            vocabulary_container = document.querySelector('.vocabulary-field')
            $(vocabulary_container).html(response)
        },
        error: function(error) {
            // Handle errors
            console.error('Error:', error);
        }
    });
}

fragmentContainer.addEventListener('click',async function(e){
    if(e.target.id === 'vocab-form-btn') {

        let stageId = document.querySelector('.stageId').value
        let levelId = document.querySelector('.levelId').value

        $.ajax({
            type: 'GET',
            url: '/admin/vocabulary/add?stageId=' + stageId + '&levelId='+ levelId,
            success: async function (response) {
                vocabulary_container = document.querySelector('.vocabulary-field')
                $(vocabulary_container).html(response)

                vocabulary_form = document.getElementById("vocabulary-form")
                vocabulary_form.addEventListener("input", function (e) {
                    const target = e.target;

                    console.log("in")
                    // Check if the target is an input in the last cell of a row
                    if (target.tagName === "INPUT" && target.parentNode.cellIndex === 1) {
                        const currentRow = target.parentNode.parentNode;
                        const lastRow = currentRow.parentNode.lastElementChild;

                        // Clone the last row if the current row is the last row
                        if (currentRow === lastRow) {
                            const newRow = currentRow.cloneNode(true);

                            // Clear input values in the new row
                            Array.from(newRow.querySelectorAll('input')).forEach(input => input.value = '');

                            currentRow.parentNode.appendChild(newRow);
                        }
                    }
                });

                if (loadedVocabJs === true)
                    return

                const script = document.createElement('script');
                const text = document.createTextNode(await AJAX(`/javascript/fragments_admin/vocabulary.js`));

                script.appendChild(text);
                loadedVocabJs = true;
                fragmentContainer.append(script)
            },
            error: function(error) {
                alert(this.url)
            }
        });
    }
})