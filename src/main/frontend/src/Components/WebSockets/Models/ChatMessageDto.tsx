
export class ChatMessageDto {
    user: any;
    message: any;
    constructor(user, message){
        this.user = user;
        this.message = message;
    }
}