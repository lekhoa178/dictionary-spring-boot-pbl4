fragmentContainer.addEventListener('click', async function (e) {
    if (e.target.id === 'add-vocab-btn') {
        let dataToSend = [];
        const rows = document.querySelectorAll('.rowData')

        let stageId = document.querySelector('.stageId').value
        let levelId = document.querySelector('.levelId').value

        for (let i = 0; i < rows.length - 1; ++i) {
            let rowData = {};

            rowData.id = {
                levelId: {
                    stageId: stageId,
                    levelId: levelId
                },
                vocabularyNum: 0
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
            console.log(rowData.type)
            dataToSend.push(rowData);
        }
        console.log(JSON.stringify(dataToSend))

        $.ajax({
            type: 'POST',
            url: '/admin/vocabulary/save',
            contentType: 'application/json',
            data: JSON.stringify(dataToSend),
            success: function (response) {
                // Handle the response from the server
                console.log("success")
                $(vocabulary_container).html(response)
            },
            error: function (error) {
                // Handle errors
                console.error('Error:', error);
            }
        });
    }
})