import React from "react";
import {Sidenav} from "../components/Sidenav";

export function PricesStatistics() {
    return (
        <div className="flex
        flex-wrap
        bg-gray-100
        w-full
        h-screen">
            <Sidenav/>
            <div className="text-gray-500 flex">
                <h1 className="font-bold text-3xl md:text-5xl lg:text-9xl text-gray-200 px-5">CheapestWay</h1>
            </div>
        </div>
    )
}