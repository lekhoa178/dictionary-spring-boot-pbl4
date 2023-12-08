let searchFriendVal = "";

fragmentContainer.addEventListener('submit', async e => {
    e.preventDefault();
    const target = e.target.querySelector('.search--friend--search');

    if (target == null) return;
    if (target.value.length === 0) return;

    searchFriendVal = target.value;
    [fragmentContainer.innerHTML] = await Promise.all([AJAX(`/searchFriend/${searchFriendVal}`)]);
})

fragmentContainer.addEventListener('click', async e => {
    if (e.target.closest('.search--friend--result-follow')) {
        const target = e.target.closest('.search--friend--result-follow');

        console.log(`/follow/${searchFriendVal}`);
        try {
            const response = await fetch(`/follow/${searchFriendVal}`, {
                method: 'POST',
                headers: {
                    'request-source': 'JS',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(parseInt(target.dataset.id))
            });

            const data = await response.text();

            if (!response.ok) throw new Error('Error response');

            fragmentContainer.innerHTML = data;
        } catch (e) {
            console.error(e.message);
        }
    } else if (e.target.closest('.search--friend--result-item')) {
        const target = e.target.closest('.search--friend--result-item');

        window.location = `/profile/${target.dataset.id}`;
    }
})