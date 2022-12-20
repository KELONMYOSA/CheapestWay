import React, {useEffect, useState} from "react";
import {PlotPriceChart} from "../components/PlotPriceChart";
import {SockJS} from "../components/SockJS";
import {useSelector} from "react-redux";
import {store} from "../store/store";
import AsyncSelect from "react-select/async";
import {SidenavPriceLatest} from "../components/SidenavPriceLatest";
// @ts-ignore
import "react-datepicker/dist/react-datepicker.css";

export function PriceChartLatest() {
    const [sockJS, setSockJS] = useState(new SockJS());
    const [cityFrom, setCityFrom] = useState("From")
    const [cityTo, setCityTo] = useState("To")
    const [showChart, setShowChart] = useState(false);
    useSelector(state => state);
    // @ts-ignore
    let data: string = JSON.stringify(store.getState().map(home => home.data)[0])
    let cities
    let citiesChart
    // @ts-ignore
    let citiesMap
    // @ts-ignore
    let prices
    // @ts-ignore
    let pricesDates
    let updateDate
    let isLoading = true

    if (data != null) {
        data = data.substring(1, data.length - 1).replaceAll("\\", "")
        cities = JSON.parse(data)
        // @ts-ignore
        citiesMap = cities.map(item => {
            const container = {};
            // @ts-ignore
            container.label = item.name;
            // @ts-ignore
            container.value = item.code;
            return container;
        })
        try {
            // @ts-ignore
            let dataChart: string = JSON.stringify(store.getState().map(home => home.data)[store.getState().length - 1])
            dataChart = dataChart.substring(1, dataChart.length - 1).replaceAll("\\", "")
            // @ts-ignore
            citiesChart = JSON.parse(dataChart)
            // @ts-ignore
            citiesChart.sort(function(a, b){
                // @ts-ignore
                return new Date(a.departure_at.substring(0, 10)) - new Date(b.departure_at.substring(0, 10));
            });
            // @ts-ignore
            prices = citiesChart.map(item => item.price)
            // @ts-ignore
            pricesDates = citiesChart.map(item => item.departure_at.substring(8, 10) + "-" + item.departure_at.substring(5, 7) + "-" + item.departure_at.substring(0, 4))
            // @ts-ignore
            updateDate = citiesChart.map(item => item.timestamp)[0]
            updateDate = updateDate.substring(8, 10) + "-" + updateDate.substring(5, 7) + "-" + updateDate.substring(0, 4)
        } catch {}

        isLoading = false
    }

    useEffect(() => {
        setTimeout(() => {
            sockJS.getCities()
        }, 1000);
    }, []);

    // @ts-ignore
    function citiesMapCut(startsWith) {
        // @ts-ignore
        const out = citiesMap.map(item => {
            const container = {};
            if (item.label.startsWith(startsWith)) {
                // @ts-ignore
                container.label = item.label;
                // @ts-ignore
                container.value = item.value;
            }
            return container;
        })
        // @ts-ignore
        return out.filter((m) => m.label?.length);
    }

    // @ts-ignore
    const handleChangeFrom = (e) => {
        setCityFrom(e.value);
    };

    // @ts-ignore
    const handleChangeTo = (e) => {
        setCityTo(e.value);
    };

    const loadOptions = (
        inputValue: string,
        // @ts-ignore
        callback: (options) => void
    ) => {
        setTimeout(() => {
            const splitted = inputValue.split("")
            const first = splitted[0].toUpperCase()
            const rest = [...splitted]
            rest.splice(0, 1)
            const result = [first, ...rest].join("")
            callback(citiesMapCut(result));
        }, 1000);
    };

    // @ts-ignore
    const inputFrom = <AsyncSelect
        className="basic-single"
        classNamePrefix="select"
        isLoading={isLoading}
        // @ts-ignore
        loadOptions={loadOptions}
        // @ts-ignore
        onChange={handleChangeFrom}
        placeholder={cityFrom}
    />

    // @ts-ignore
    const inputTo = <AsyncSelect
        className="basic-single"
        classNamePrefix="select"
        isLoading={isLoading}
        // @ts-ignore
        loadOptions={loadOptions}
        // @ts-ignore
        onChange={handleChangeTo}
        placeholder={cityTo}
    />

    const getPrices = () => {
        sockJS.getPricesLatest(cityFrom, cityTo)
        setTimeout(() => { setShowChart(true); }, 2000);

        setShowChart(false)
    }

    const plotChart = PlotPriceChart(pricesDates, prices)

    return (
        <div className="flex
        bg-gray-100
        w-full
        h-screen">
            <SidenavPriceLatest/>
            <div className="flex-wrap text-gray-500 h-20">
                <div className="flex flex-wrap">
                    <div className="w-48 ml-5 mr-0 mt-6">{inputFrom}</div>
                    <div className="w-48 ml-5 mr-0 mt-6">{inputTo}</div>
                    <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded ml-5 mr-0 mt-5"
                            onClick={getPrices}>Search
                    </button>
                </div>
                <p className="font-bold m-5">{showChart ? "Last update date: " + updateDate : null}</p>
                <>{showChart ? plotChart : null}</>
            </div>
            <>{sockJS.sockJsClient}</>
        </div>
    )
}