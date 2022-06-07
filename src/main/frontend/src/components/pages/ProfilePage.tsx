import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Player } from "../../data-interfaces";


export default function ProfilePage() {

const {id} = useParams();
const [player, setPlayer] = useState<Player>();

useEffect(() => {
    axios
      .get<Player>("players/"+id)
      .then((response) => {
        setPlayer(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

return (
    <div>This is profile page of user {player.fullName}</div>
)
}
