fragmentContainer.addEventListener('submit', async e => {
    e.preventDefault();
    const target = e.target.querySelector('.search--friend--search');

    if (target == null) return;

    console.log(`/searchFriend/${target.value}`);
    [fragmentContainer.innerHTML] = await Promise.all([AJAX(`/searchFriend/${target.value}`)]);
})