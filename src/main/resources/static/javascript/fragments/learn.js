fragmentContainer.addEventListener('click', function(e) {
    const el = e.target.closest('.level');
    if (el == null) return;

    const startPanel = document.querySelector('.floating-start-panel')
    const level = el.dataset.levelId;
    const stageId = el.dataset.stageId;
    const color = window.getComputedStyle(el).backgroundColor;

    let content = '';
    if (el.classList.contains('level--inactive')) {
        startPanel.style.backgroundColor = '#f6f6f6';
        startPanel.classList.add('floating-start-panel--inactive');
        content =
            `<h2>Cửa ${level}</h2>
                <button class="floating-start--btn floating-start--btn-inactive"
                data-test="${stageId} ${level}">BẮT ĐẦU + 10 KN</button>`;
    }
    else {
        startPanel.style.backgroundColor = color;
        startPanel.classList.remove('floating-start-panel--inactive');
        content =
            `<h2>Cửa ${level}</h2>
                <button class="floating-start--btn"
                data-test="${stageId} ${level}">BẮT ĐẦU + 10 KN</button>`;
    }

    startPanel.innerHTML = content;

    const elBound = el.getBoundingClientRect();

    const mouseX = elBound.x + window.scrollX + 60;
    const mouseY = elBound.y + window.scrollY + 40;

    startPanel.classList.remove('hide');
    startPanel.style.left = mouseX + 'px';
    startPanel.style.top = mouseY + 'px';

    e.stopPropagation();

});

fragmentContainer.addEventListener('click', function(e) {
    const el = e.target.closest('.floating-start--btn');

    if (el == null) return;

    const stageId = el.dataset.test.split(" ")[0];
    const levelId = el.dataset.test.split(" ")[1];

    if (el.closest('.floating-start-panel--inactive')) return;

    window.location = `/lesson/${stageId}/${levelId}`;
});

document.addEventListener('click', function(e) {
    const startPanel = document.querySelector('.floating-start-panel')

    if (startPanel == null) return;

    if (startPanel.contains(e.target)) return;

    startPanel.classList.add('hide');
});