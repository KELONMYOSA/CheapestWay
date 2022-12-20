import chatAPI from "../components/Requests";
// @ts-ignore
import SockJsClient from 'react-stomp';
import {useDispatch} from "react-redux";
import {addState} from "../store/actions";

export class SockJS {
    dispatch = useDispatch();
    userID : string = "id" + Math.random().toString(16).slice(2);

    getCities = () => {
        chatAPI.getCities(this.userID).then(res => {
            console.log('Get Cities', res);
        }).catch(err => {
            console.log('Error Occurred while sending message to api');
        })
    }

    getCheapAirlines = () => {
        chatAPI.getCheapAirlines(this.userID).then(res => {
            console.log('Get Cheap Airlines', res);
        }).catch(err => {
            console.log('Error Occurred while sending message to api');
        })
    }

    getPriceAvgWeek = () => {
        chatAPI.getPriceAvgWeek(this.userID).then(res => {
            console.log('Get Avg Price Week', res);
        }).catch(err => {
            console.log('Error Occurred while sending message to api');
        })
    }

    getPriceAvg = () => {
        chatAPI.getPriceAvg(this.userID).then(res => {
            console.log('Get Avg Price', res);
        }).catch(err => {
            console.log('Error Occurred while sending message to api');
        })
    }

    getFlightNumbers = () => {
        chatAPI.getFlightNumbers(this.userID).then(res => {
            console.log('Get Flight Numbers', res);
        }).catch(err => {
            console.log('Error Occurred while sending message to api');
        })
    }

    getLowcosters = () => {
        chatAPI.getLowcosters(this.userID).then(res => {
            console.log('Get Lowcosters', res);
        }).catch(err => {
            console.log('Error Occurred while sending message to api');
        })
    }

    getPricesOnDate = (departure : string, arrival : string, date : string) => {
        chatAPI.getPricesOnDate(this.userID, departure, arrival, date).then(res => {
            console.log('Get Prices On Date', res);
        }).catch(err => {
            console.log('Error Occurred while sending message to api');
        })
    }

    getPricesLatest = (departure : string, arrival : string) => {
        chatAPI.getPricesLatest(this.userID, departure, arrival).then(res => {
            console.log('Get Prices Latest', res);
        }).catch(err => {
            console.log('Error Occurred while sending message to api');
        })
    }

    onMessageReceived = (msg: JSON) => {
        const messageString: string = JSON.stringify(msg);
        const messageJSON = JSON.parse(messageString)
        console.log('Message Received - ' + messageJSON.userID);
        if (messageJSON.userID === this.userID) {
            this.dispatch(addState({
                data: messageJSON.message
            }))
        }
    }

    onConnected = () => {
        console.log("Connected - " + this.userID);
    }

    sockJsClient = <SockJsClient
        url='https://kelonmyosa.ddns.net:8443/ws'
        topics={["/user/" + this.userID + "/queue/messages"]}
        onConnect={() => this.onConnected()}
        onDisconect={() => {console.log("Disconnected")}}
        onMessage={(msg: JSON) => this.onMessageReceived(msg)}
    />
}