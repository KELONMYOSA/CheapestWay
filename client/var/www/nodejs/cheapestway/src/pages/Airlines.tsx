import React, {useEffect, useState} from "react";
import {SidenavAirlines} from "../components/SidenavAirlines";
import {PlotPieChart} from "../components/PlotPieChart";
import {SockJS} from "../components/SockJS";
import {useSelector} from "react-redux";
import {store} from "../store/store";
import {PlotBarChart} from "../components/PlotBarChart";

export function Airlines(this: any) {
    const [sockJS, setSockJS] = useState(new SockJS());

    useSelector(state => state);
    // @ts-ignore
    let lowcosters: string = JSON.stringify(store.getState().map(home => home.data)[0])
    let lowcostersJSON

    if (lowcosters != null) {
        lowcosters = lowcosters.substring(1, lowcosters.length - 1).replaceAll("\\", "")
        lowcostersJSON = JSON.parse(lowcosters)
    }

    // @ts-ignore
    let flightNumbers: string = JSON.stringify(store.getState().map(home => home.data)[1])
    let flightNumbersJSON
    let flightNumbersX
    let flightNumbersY

    if (flightNumbers != null) {
        flightNumbers = flightNumbers.substring(1, flightNumbers.length - 1).replaceAll("\\", "")
        flightNumbersJSON = JSON.parse(flightNumbers)
        // @ts-ignore
        flightNumbersX = flightNumbersJSON.map(item => item.name)
        // @ts-ignore
        flightNumbersY = flightNumbersJSON.map(item => item.value)
    }

    // @ts-ignore
    let cheapAirlines: string = JSON.stringify(store.getState().map(home => home.data)[2])
    let cheapAirlinesJSON
    let cheapAirlinesX
    let cheapAirlinesY

    if (cheapAirlines != null) {
        cheapAirlines = cheapAirlines.substring(1, cheapAirlines.length - 1).replaceAll("\\", "")
        cheapAirlinesJSON = JSON.parse(cheapAirlines)
        // @ts-ignore
        cheapAirlinesX = cheapAirlinesJSON.map(item => item.airport)
        // @ts-ignore
        cheapAirlinesY = cheapAirlinesJSON.map(item => item.price)
    }

    useEffect(() => {
        setTimeout(() => {
            sockJS.getLowcosters()
        }, 800);
        setTimeout(() => {
            sockJS.getFlightNumbers()
        }, 10);
        setTimeout(() => {
            sockJS.getCheapAirlines()
        }, 2000);
    }, []);

    const pieLowcosters = PlotPieChart(lowcostersJSON, "Lowcosters")
    const barСountAirlinesFlights = PlotBarChart(flightNumbersX, flightNumbersY, "Top 10 by number of flights", '500px')
    const barCheapAirlines = PlotBarChart(cheapAirlinesX, cheapAirlinesY, "Top 10 the cheapest airlines", '500px')

    return (
        <div className="flex
        bg-gray-100
        w-full
        h-screen">
            <SidenavAirlines/>
            <div className="flex flex-wrap
            text-gray-500">
                <div className="mt-5">{pieLowcosters}</div>
                <div className="mt-5">{barСountAirlinesFlights}</div>
                <div className="mt-5">{barCheapAirlines}</div>
            </div>
            <>{sockJS.sockJsClient}</>
        </div>
    )
}