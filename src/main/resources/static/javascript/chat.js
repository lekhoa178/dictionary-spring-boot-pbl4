// chat.js
const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);


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
stompClient.connect({}, function (frame) {
    const accountId = document.getElementById("metadata").dataset.accountId;
    stompClient.subscribe(`/topic/messages/${accountId}`, function (message) {
        console.log("Into receiver: ",message);
        // Handle incoming messages and update the UI
        const messageData = JSON.parse(message.body);
        showNotify(messageData);
        refreshStyleSheet();
    });
});


function sendMyMessage(friendId) {
    const accountId = document.getElementById("metadata").dataset.accountId;
    var messageInput = document.querySelector('.message-input-'+friendId);
    var messageText = messageInput.value.trim();
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
        var chatMessage = {
            content: messageText,
            senderUserId: accountId,
            receiverUserId: friendId,

        };
        stompClient.send(`/app/chat.sendMessage/${friendId}`, {}, JSON.stringify(chatMessage));

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
function clickPress(friendId) {
    if (event.keyCode == 13) {
        sendMyMessage(friendId);
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
    showFollowbtn.style.display = "flex";
}
function openPanelFollow() {
    var panelFollow = document.getElementById("follow-panel");
    panelFollow.hidden = false;
    var missonContainer = document.getElementById("mission-container-side");
    missonContainer.style.display = "none";
    var showFollowbtn = document.getElementById("show-follow-button");
    showFollowbtn.style.display = "none";

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
            console.log("response find friend: ",response)
            friend = response;
            $.ajax({
                type: 'GET',
                url: `/api/messageList/${accountId}/${friendId}`,
                success: function (response) {
                    let messages = showMessage(response);
                    var container = document.getElementById(`message-${friendId}`);
                    if (container == null){
                        var listChat = document.querySelector(".chat-container-message");
                        var chatContainer = `
                                               <div class="chat-container" id="chat-container-${friendId}">
                                                    <div class="chat-header">
                                                        <h3>${friend.name}</h3>
                                                        <div>
                                                            <button class="button-chat" onclick="toggleChat()">_</button>
                                                            <button class="button-chat" onclick="closeChat(${friendId})">x</button>
                                    
                                                        </div>
                                                    </div>
                                                    <div class="chat-messages" id="chat-messages-${friendId}">
                                                    ${messages}
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
                                                        " placeholder="Type your message..." onkeypress="clickPress(${friendId})">
                                                        <button class="button-chat send-message-button " onclick="sendMyMessage(${friendId})">
                                                            <i class="fa-solid fa-paper-plane" style="font-size: 20px;"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            `;
                        listChat.innerHTML = chatContainer;
                        // Scroll to the bottom to show the latest message
                        chatContainer.scrollTop = chatContainer.scrollHeight;
                        refreshStyleSheet();
                        let chatMessages = document.getElementById("chat-messages-"+friendId);
                        console.log("Message height : ",chatMessages.scrollHeight);
                        chatMessages.scrollTop = chatMessages.scrollHeight;


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


function showMessage (messageList) {
    const accountId = document.getElementById("metadata").dataset.accountId;
    console.log("Account id current: ",accountId);
    let messages = ``;
    messageList.forEach(message => {
        if(accountId == message.sender.accountId){
            messages += `
                 <div class="my-message">
                    <p class="my-text-message"> 
                        ${message.content}
                    </p>
                </div>
            `
        }
        else {
            messages += `
                 <div class="friend-message">
                    <div class="avatar-message">${message.sender.username[0].toUpperCase()}</div>
                    <p class="text-message">
                        ${message.content}
                    </p>
                </div>
            `
        }
    })
    return messages;
}
function showNotify(message) {
    const token = getCookie("jwtToken");
    $.ajax({
        type: 'GET',
        url: `/dictionary/accountById/${message.senderUserId}`,
        headers: {
            'Authorization': 'Bearer ' + token,
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (response) {
            const name = response.name;
            const letterFirst = name[0].toUpperCase();
            let containerElement = document.querySelector('.main-container');
            let notify = document.createElement("div");
            notify.classList.add("notification");
            let content = `
                                <div class="notification-header">
                                    <h3 class="notification-title">New notification</h3>
                                    <i class="fa fa-times notification-close" onclick="closeNotify()"></i>
                                </div>
                                <div class="notification-container">
                                    <div class="notification-media">
                                        <div class="notification-user-avatar">${letterFirst}</div>
                                        <i class="fa-solid fa-bell notification-reaction"></i>
                                    </div>
                                    <div class="notification-content">
                                        <p class="notification-text">
                                            <strong>${name}</strong> send you a message: ${message.content}
                                           
                                        </p>
                                        <span class="notification-timer">a few seconds ago</span>
                                    </div>
                                    <span class="notification-status"></span>
                                </div>
                            `;
            notify.innerHTML = content;
            containerElement.appendChild(notify);
            setTimeout(() => {
                notify.remove();
            }, 13000);
            let friendChat = document.getElementById('chat-messages-'+message.senderUserId);
            if(friendChat != null){

                let friendElement = document.createElement("div");
                friendElement.classList.add("friend-message");
                let contentMess = `
                     <div class="avatar-message">${letterFirst}</div>
                    <p class="text-message">
                        ${message.content}
                    </p>
                `;
                friendElement.innerHTML = contentMess;
                friendChat.appendChild(friendElement);
                friendChat.scrollTop = friendChat.scrollHeight;
                refreshStyleSheet();
            }
            refreshStyleSheet();
        },
        error: function (error) {
            alert(error);
        }
    })

}
function closeNotify(){
    var notify = document.querySelector('.notification');
    notify.remove();
}