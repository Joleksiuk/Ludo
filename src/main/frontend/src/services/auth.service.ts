import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
    login(nickname: string, password_hash: string) {
    return axios
      .post(API_URL + "signin", {
        nickname,
        password_hash
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem("player", JSON.stringify(response.data));
        }
        return response.data;
      });
  }
  logout() {
    localStorage.removeItem("player");
  }
  register(nickname: string, password_hash: string) {
    return axios.post(API_URL + "signup", {
        nickname,
        password_hash
    });
  }
  getCurrentPlayer() {
    const playerStr = localStorage.getItem('player');
    if (playerStr) return JSON.parse(playerStr);
    return null;
  }
}
export default new AuthService();