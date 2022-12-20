import React, {forwardRef, useEffect, useState} from "react";
import {SidenavPrice} from "../components/SidenavPrice";
import {PlotPriceChart} from "../components/PlotPriceChart";
import {SockJS} from "../components/SockJS";
import {useSelector} from "react-redux";
import {store} from "../store/store";
import AsyncSelect from "react-select/async";
// @ts-ignore
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export function PriceChart() {
    const [sockJS, setSockJS] = useState(new SockJS());
    const [cityFrom, setCityFrom] = useState("From")
    const [cityTo, setCityTo] = useState("To")
    const [date, setDate] = useState(new Date());
    const [showChart, setShowChart] = useState(false);
    // @ts-ignore
    const DateInput = forwardRef(({ value, onClick }, ref) => (
        // @ts-ignore
        <button className="bg-white hover:border-blue-400 border-solid border-gray-200 border-2 text-gray-500 py-2 px-4 rounded" onClick={onClick} ref={ref}>
            {value}
        </button>
    ));
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
                return new Date(a.timestamp) - new Date(b.timestamp);
            });
            // @ts-ignore
            prices = citiesChart.map(item => item.price)
            // @ts-ignore
            pricesDates = citiesChart.map(item => item.timestamp.substring(8, 10) + "-" + item.timestamp.substring(5, 7) + "-" + item.timestamp.substring(0, 4))
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
        sockJS.getPricesOnDate(cityFrom, cityTo, date.getFullYear().toString() + "-" + date.getMonth() + "-" + date.getDate())
        setTimeout(() => { setShowChart(true); }, 2000);

        setShowChart(false)
    }

    const plotChart = PlotPriceChart(pricesDates, prices)

    return (
        <div className="flex
        bg-gray-100
        w-full
        h-screen">
            <SidenavPrice/>
            <div className="flex-wrap text-gray-500 h-20">
                <div className="flex flex-wrap">
                    <div className="w-48 ml-5 mr-0 mt-6">{inputFrom}</div>
                    <div className="w-48 ml-5 mr-0 mt-6">{inputTo}</div>
                    <div className="w-24 ml-5 mr-5 mt-5">
                        <DatePicker selected={date}
                                    dateFormat="dd/MM/yyyy"
                                    customInput={<DateInput />}
                                    onChange={
                            // @ts-ignore
                            (date) => setDate(date)}/>
                    </div>
                    <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded ml-5 mr-0 mt-5"
                            onClick={getPrices}>Search
                    </button>
                </div>
                <>{showChart ? plotChart : null}</>
            </div>
            <>{sockJS.sockJsClient}</>
        </div>
    )
}