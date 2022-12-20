import {Link} from "react-router-dom";

export function SidenavPriceAvg() {
    return (
        <nav className="w-50
        h-full
        shadow-md
        bg-white">
            <ul>
                <li>
                    <Link to="/statistics/price" className="flex
                    items-center
                    text-sm
                    py-4
                    px-4
                    h-12
                    overflow-hidden
                    text-gray-700
                    text-ellipsis
                    whitespace-nowrap
                    rounded
                    hover:text-gray-900
                    hover:bg-gray-100
                    transition
                    duration-300
                    ease-in-out"
                          data-mdb-ripple="true" data-mdb-ripple-color="dark">Price history</Link>
                </li>
                <li>
                    <Link to="/statistics/price-latest" className="flex
                    items-center
                    text-sm
                    py-4
                    px-4
                    h-12
                    overflow-hidden
                    text-gray-700
                    text-ellipsis
                    whitespace-nowrap
                    rounded
                    hover:text-gray-900
                    hover:bg-gray-100
                    transition
                    duration-300
                    ease-in-out"
                          data-mdb-ripple="true" data-mdb-ripple-color="dark">Price latest</Link>
                </li>
                <li>
                    <Link to="/statistics/price-avg" className="flex
                    items-center
                    text-sm
                    py-4
                    px-4
                    h-12
                    overflow-hidden
                    text-gray-700
                    text-ellipsis
                    whitespace-nowrap
                    rounded
                    bg-gray-100
                    hover:text-gray-900
                    hover:bg-gray-100
                    transition
                    duration-300
                    ease-in-out"
                          data-mdb-ripple="true" data-mdb-ripple-color="dark">Price average</Link>
                </li>
                <li>
                    <Link to="/statistics/airlines" className="flex
                    items-center
                    text-sm
                    py-4
                    px-4
                    h-12
                    overflow-hidden
                    text-gray-700
                    text-ellipsis
                    whitespace-nowrap
                    rounded hover:text-gray-900
                    hover:bg-gray-100 transition
                    duration-300 ease-in-out"
                          data-mdb-ripple="true" data-mdb-ripple-color="dark">Airlines</Link>
                </li>
            </ul>
        </nav>
    )
}
