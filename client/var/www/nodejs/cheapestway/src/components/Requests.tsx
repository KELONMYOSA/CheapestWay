import Axios from "axios";

const api = Axios.create({
    baseURL: 'https://kelonmyosa.ddns.net:8443/',
});

const chatAPI = {
    getCities: (userID : string) => {
        return api.get(`cities?userID=` + userID);
    },

    getCheapAirlines: (userID : string) => {
        return api.get(`cheapairlines?userID=` + userID);
    },

    getPriceAvgWeek: (userID : string) => {
        return api.get(`dayofweek?userID=` + userID);
    },

    getPriceAvg: (userID : string) => {
        return api.get(`avgprice?userID=` + userID);
    },

    getFlightNumbers: (userID : string) => {
        return api.get(`flightnumbers?userID=` + userID);
    },

    getLowcosters: (userID : string) => {
        return api.get(`lowcosters?userID=` + userID);
    },

    getPricesOnDate: (userID : string, departure : string, arrival : string, date : string) => {
        return api.get(`pricesondate?userID=` + userID + `&departure=` + departure + `&arrival=` + arrival + `&date=` + date);
    },

    getPricesLatest: (userID : string, departure : string, arrival : string) => {
        return api.get(`priceslatest?userID=` + userID + `&departure=` + departure + `&arrival=` + arrival);
    }
}

export default chatAPI;