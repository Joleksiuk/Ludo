export default function authHeader() {
    const playerStr = localStorage.getItem("player");
    let player = null;
    if (playerStr)
      player = JSON.parse(playerStr);
    if (player && player.accessToken) {
      return { Authorization: 'Bearer: ' + player.accessToken };
    } else {
      return {};
    }
  }
  export{}