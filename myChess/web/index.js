function submit() {
  document.getElementById('response').value = '';
  const method = document.getElementById('method').value;
  const endpoint = document.getElementById('handleBox').value;
  const requestBody = document.getElementById('requestBox').value;
  const authToken = document.getElementById('authToken').value;

  if (endpoint && method) {
    send(endpoint, requestBody, method, authToken);
  }

  return false;
}

function send(path, params, method, authToken) {
  params = !!params ? params : undefined;
  fetch(path, {
    method: method,
    body: params,
    headers: {
      Authorization: authToken,
      'Content-Type': 'application/json',
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw Error(response.statusText);
      }
      return response.json();
    })
    .then((data) => {
      document.getElementById('authToken').value = data.authToken || authToken || 'none';
      document.getElementById('response').innerText = JSON.stringify(data, null, 2);
    })
    .catch((reason) => {
      document.getElementById('response').innerText = reason;
    });
}

function displayRequest(method, endpoint, request) {
  document.getElementById('method').value = method;
  document.getElementById('handleBox').value = endpoint;
  const body = request ? JSON.stringify(request, null, 2) : '';
  document.getElementById('requestBox').value = body;
  document.getElementById('response').innerText = '';
}

function clearDB() {
  displayRequest('POST', '/clear', null);
}
function login() {
  displayRequest('POST', '/user/login', { username: 'username', password: 'password' });
}
function register() {
  displayRequest('POST', '/user/register', { username: 'username', password: 'password', email: 'email' });
}
function logout() {
  displayRequest('POST', '/user/logout', null);
}
function gamesList() {
  displayRequest('GET', '/games/list', null);
}
function createGame() {
  displayRequest('POST', '/games/create', { gameName: 'gameName' });
}
function joinGame() {
  displayRequest('POST', '/games/join', { playerColor: 'WHITE|BLACK|<empty>', gameID: 0 });
}
