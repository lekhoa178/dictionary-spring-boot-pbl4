// chat.js

const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

const userId = document.getElementById("metadata").dataset.accountId;
function getCookie(name) {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        if (cookie.startsWith(name + '=')) {
            return cookie.substring(name.length + 1);
        }
    }
    return null;
}

function startChat() {
    // Get the selected username
    username = document.getElementById('usernameInput').value;

    // Hide user selection container
    document.getElementById('userSelectionContainer').style.display = 'none';

    // Show chat container
    document.getElementById('chatContainer').style.display = 'block';


    // Connect to WebSocket server
    stompClient.connect({}, function (frame) {
        stompClient.subscribe(`'/topic/messages/${userId}`, function (message) {
            // Handle incoming messages and update the UI
            const messageData = JSON.parse(message.body);

            // Assuming you have an element with id "chatMessages" to display messages
            const chatMessagesElement = document.querySelector('.view-container');

            var chatContainer = `
                     <div className="chat-container" id="chat-container">
                <div className="chat-header">
                    <span>John Doe</span>
                    <div>
                        <button onClick="toggleChat()">_</button>
                        <button onClick="closeChat()">x</button>
        
                    </div>
                </div>
                <div className="chat-messages" id="chat-messages">
                    <div className="friend-message">
                        <div className="avatar-message">N</div>
                        <p className="text-message">
                            Hello from friend
                        </p>
                    </div>
                    <div className="my-message">
                        <p className="my-text-message">
                            Hello from friend asf UOPf a ifug AO Auflah
                        </p>
                    </div>
                </div>
                <div className="chat-input">
                    <input type="text" id="message-input" placeholder="Type your message..." onKeyPress="clickPress(event)">
                        <button className="send-message-button" onClick="sendMyMessage()"><i className="fa-solid fa-paper-plane"
                                                                                         style="font-size: 20px;"></i></button>
                </div>
            </div>
            `;

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
    const accountId = document.getElementById("metadata").dataset.accountId;
    console.log("Account id current: ",accountId);
    stompClient.subscribe(`/chat.sendMessage/${accountId}`, function (message) {
        // Handle incoming messages and update the UI
        const messageData = JSON.parse(message.body);
        displayMessage(messageData);
    });
});
function sendMyMessage(friendId) {
    var messageInput = document.querySelector('.message-input-'+friendId);
    console.log("Message input: ", messageInput);
    var messageText = messageInput.value.trim();
    console.log("Message text: ",messageText);
    if (messageText !== '') {
        var chatMessages = document.getElementById('chat-messages-'+friendId);
        var myMess = document.createElement("div");
        myMess.classList.add("my-message");
        var pNode = ` <p class="my-text-message">
                                ${messageText}
                            </p>`;
        myMess.innerHTML = pNode;
        chatMessages.appendChild(myMess);
        // Clear input after sending message
        messageInput.value = '';
        // Scroll to the bottom of the chat container
        chatMessages.scrollTop = chatMessages.scrollHeight;
        $.ajax({
            type: 'GET',
            url: `/dictionary/accountById/${friendId}`,
            headers: {
                'Authorization': 'Bearer ' + token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (response) {
            },
            error: function (error) {
                alert(error);
            }
        })
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
function closeChat(friendId) {
    var chatContainer = document.getElementById('chat-container-'+friendId);
    chatContainer.remove();
}
function closePanelFollow() {
    var panelFollow = document.getElementById("follow-panel");
    panelFollow.hidden = true;
    var missonContainer = document.getElementById("mission-container-side");
    missonContainer.style.display = "grid";
    var showFollowbtn = document.getElementById("show-follow-button");
    showFollowbtn.hidden = false;
}
function openPanelFollow() {
    var panelFollow = document.getElementById("follow-panel");
    panelFollow.hidden = false;
    var missonContainer = document.getElementById("mission-container-side");
    missonContainer.style.display = "none";
    var showFollowbtn = document.getElementById("show-follow-button");
    showFollowbtn.hidden = true;
}
function refreshStyles() {
    var links = document.getElementsByTagName("link");
    for (var i = 0; i < links.length; i++) {
        var href = links[i].href;
        links[i].href = href + "?refresh=" + (new Date()).getMilliseconds();
    }

}
function refreshStyleSheet() {
    var styleSheet = document.getElementById("ChatStyleSheet");
    if (styleSheet) {
        var href = styleSheet.href;
        styleSheet.href = href + "?refresh=" + (new Date()).getMilliseconds();
    }
}
function handleOnclickChatFriend(e){
    const token = getCookie("jwtToken");
    const accountId = document.getElementById("metadata").dataset.accountId;
    var query = e.target.closest('.chat-friend');
    var dataset = query.dataset;
    var friendId = query.dataset.friendId;
    let friend = {};
    $.ajax({
        type: 'GET',
        url: `/dictionary/accountById/${friendId}`,
        headers: {
            'Authorization': 'Bearer ' + token,
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (response) {
            console.log("Response find friend: ",response);
            friend = response;
            $.ajax({
                type: 'GET',
                url: `/api/messageList/${accountId}/${friendId}`,
                success: function (response) {
                    var container = document.getElementById(`message-${friendId}`);
                    if (container == null){
                        var listChat = document.querySelector(".chat-container-message");
                        var chatContainer = `
                                               <div class="chat-container" id="chat-container-${friendId}">
                                                    <div class="chat-header">
                                                        <h3>${friend.name}</h3>
                                                        <div>
                                                            <button onclick="toggleChat()">_</button>
                                                            <button onclick="closeChat(${friendId})">x</button>
                                    
                                                        </div>
                                                    </div>
                                                    <div class="chat-messages" id="chat-messages-${friendId}">
                                                        <div class="friend-message">
                                                            <div class="avatar-message">N</div>
                                                            <p class="text-message">
                                                                Hello from friend
                                                            </p>
                                                        </div>
                                                        <div class="my-message">
                                                            <p class="my-text-message">
                                                                Hello from friend asf UOPf a ifug AO Au
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <div class="chat-input" >
                                                        <input type="text" class="message-input-${friendId}" style=" 
                                                            outline: 1px ridge rgba(86, 129, 238, 0.6);
                                                            border-radius: 2rem;
                                                            padding: 5px 15px;
                                                            font-size: 14px;
                                                            flex: 1;
                                                            border: none;
                                                            :focus {
                                                            outline: 1px solid rgba(86, 129, 238, 0.6);
                                                            };
                                                        " placeholder="Type your message..." onkeypress="clickPress(event)">
                                                        <button class="send-message-button" onclick="sendMyMessage(${friendId})">
                                                            <i class="fa-solid fa-paper-plane" style="font-size: 20px;"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            `;
                        listChat.innerHTML = chatContainer;
                        refreshStyleSheet();
                        // refreshStyles();
                    }


                },
                error: function (error) {
                    alert(this.url);
                },
            });
        },
        error: function (error) {
            alert(this.url);
        },
    });
    closePanelFollow();

}