let stompNewUserClient = null;
let stompAllUserClient = null;

const setConnected = (connected) => {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#chatLine").show();
	} else {
		$("#chatLine").hide();
	}
	$("#message").html("");
};

const connect = () => {
	stompAllUserClient = Stomp.over(new SockJS('/users-websockets'));
	stompAllUserClient.connect({}, (frame) => {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompAllUserClient.subscribe('/topic/usersList', (message) => showGreeting(JSON.parse(message.body).messageStr));
		stompAllUserClient.send("/app/getAllUsers", {}, null);
	});

	stompNewUserClient = Stomp.over(new SockJS('/users-websockets'));
	stompNewUserClient.connect({}, (frame) => {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompNewUserClient.subscribe('/topic/userCreateStarted', (message) => alert("User creation Started"));
	});
};

const disconnect = () => {
	if (stompNewUserClient !== null) {
		stompNewUserClient.disconnect();
	}
	if (stompAllUserClient !== null) {
		stompAllUserClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
};

const sendMsg = () => {
	var userData = {
		name: $("#holder-input-name").val(),
		login: $("#holder-input-login").val(),
		password: $("#holder-input-password").val()
	};
	stompNewUserClient.send("/app/newUserMessage", {}, JSON.stringify(userData));
};


const showGreeting = (messageStr) => {
	$("#usersList").html("");
	messageStr.forEach((item, index, array) => {
		var str = "<tr>" +
			"<td>" + item.id + "</td>" +
			"<td>" + item.name + "</td>" +
			"<td>" + item.login + "</td>" +
			"<td>" + item.password + "</td>" +
			"</tr>";
		$("#usersList")
			.append(str);
	});
};


$(function() {
	$("form").on('submit', (event) => {
		event.preventDefault();
	});
	$("#connect").click(connect);
	$("#disconnect").click(disconnect);
	$("#send").click(sendMsg);
});