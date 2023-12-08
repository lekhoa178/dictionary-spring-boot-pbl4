// chat.js

const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

let username;

function startChat() {
    // Get the selected username
    username = document.getElementById('usernameInput').value;

    // Hide user selection container
    document.getElementById('userSelectionContainer').style.display = 'none';

    // Show chat container
    document.getElementById('chatContainer').style.display = 'block';

    // Connect to WebSocket server
    stompClient.connect({}, function (frame) {
        stompClient.subscribe(`/user/${username}/topic/messages`, function (message) {
            // Handle incoming messages and update the UI
            const messageData = JSON.parse(message.body);

            // Assuming you have an element with id "chatMessages" to display messages
            const chatMessagesElement = document.getElementById('chatMessages');

            // Create a new message element
            const newMessageElement = document.createElement('div');
            newMessageElement.className = 'message';
            newMessageElement.innerHTML = `<strong>${messageData.sender.username}</strong>: ${messageData.content}`;

            // Append the new message to the chatMessagesElement
            chatMessagesElement.appendChild(newMessageElement);

            // Scroll to the bottom to show the latest message
            chatMessagesElement.scrollTop = chatMessagesElement.scrollHeight;
        });
    });
}
function sendMessage() {
    const messageInput = document.getElementById('messageInput').value;

    // Assuming you have a receiver username (replace 'receiverUsername' with the actual username)
    const receiverUsername = 'receiverUsername';

    const chatMessage = {
        sender: username,
        receiver: receiverUsername,
        content: messageInput
    };
    // Send the message to the server
    stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));
    // Clear the input field after sending the message
    document.getElementById('messageInput').value = '';
}
// Your JavaScript code for sending messages and updating the UI goes here

function displayMessage(messageData) {
    // Assuming you have an element with id "chatMessages" to display messages
    const chatMessagesElement = document.getElementById('chatMessages');

    // Create a new message element
    const newMessageElement = document.createElement('div');
    newMessageElement.className = 'message';
    newMessageElement.innerHTML = `<strong>${messageData.sender}</strong>: ${messageData.content}`;

    // Append the new message to the chatMessagesElement
    chatMessagesElement.appendChild(newMessageElement);

    // Scroll to the bottom to show the latest message
    chatMessagesElement.scrollTop = chatMessagesElement.scrollHeight;
}

stompClient.connect({}, function (frame) {
    stompClient.subscribe(`/user/${username}/topic/messages`, function (message) {
        // Handle incoming messages and update the UI
        const messageData = JSON.parse(message.body);
        displayMessage(messageData);
    });
});
function sendMyMessage() {
    var messageInput = document.getElementById('message-input');
    var messageText = messageInput.value.trim();

    if (messageText !== '') {
        var chatMessages = document.getElementById('chat-messages');
        var messageDiv = document.createElement('div');
        messageDiv.classList.add("my-message");
        var messageMyText = document.createElement("p");
        messageMyText.classList.add("my-text-message");
        messageDiv.appendChild(messageMyText);
        messageMyText.textContent = messageText;
        chatMessages.appendChild(messageDiv);

        // Clear input after sending message
        messageInput.value = '';

        // Scroll to the bottom of the chat container
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }
}

function toggleChat() {
    var chatContainer = document.getElementById('chat-container');
    var chatToggleButton = document.getElementById('chat-toggle-button');

    if (chatContainer.style.transform === 'translateY(90%)') {
        chatContainer.style.transform = 'translateY(0%)';
        chatContainer.style.transform = 'translateX(-50%)';
        chatToggleButton.innerHTML = '&#x2715;';
    } else {
        chatContainer.style.transform = 'translateY(90%)';
        chatToggleButton.innerHTML = '&#x27A1;';
    }
}
function clickPress(event) {
    if (event.keyCode == 13) {
        sendMessage();
    }
}
function closeChat() {
    var chatContainer = document.getElementById('chat-container');
    chatContainer.remove();
}