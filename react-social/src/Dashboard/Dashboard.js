import React, { useState, useRef, useEffect } from "react"
import ServicesList from "./ServicesList";
import WidgetsList from "./WidgetsList";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import '../styles/Dashboard.css';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Alert from 'react-s-alert';

const LOCAL_STORAGE_KEY = 'servicesApp.services'

const Dashboard = () => {

    const range = 9;

    const options = [
        {
            label: "-- Pick your services --",
            value: ""
        },
        {
            label: "Weather report",
            value: "weather"
        },
        {
            label: "News",
            value: "news"
        },
        {
            label: "Deezer",
            value: "deezer"
        },
        {
            label: "Youtube",
            value: "youtube"
        },
        {
            label: "Ringtone",
            value: "ringtone"
        },
        {
            label: "Steam",
            value: "steam"
        },
        {
            label: "Exchange rate",
            value: "exchange"
        },
        {
            label: "Timezone",
            value: "timezone"
        }
    ]

    const getJSON = (e, select, value) => {
        e.preventDefault();
        switch (select) {
            case "youtube":
                const getYoutube = async (arg) => {
                    const value = arg;
                    const response = await fetch('http://localhost:8080/auth/services/yt/search?value=' + value);
                    const youtubeData = await response.json();
                    console.log(youtubeData);
                    youtubeData["items"].map(item => {
                        return setWidgets(prevWidgets => {
                            return [{ value: value, channelTitle: item["snippet"]["channelTitle"], videoTitle: item["snippet"]["title"], miniature: item["snippet"]["thumbnails"]["medium"]["url"], videoDescription: item["snippet"]["description"] }, ...prevWidgets]
                        });
                    })
                    console.log(widgets);
                }
                getYoutube(value);
                break;
            case "weather":
                const getWeather = async (arg) => {
                    const value = arg;
                    const response = await fetch('http://localhost:8080/auth/services/weather/temp?value=' + value);
                    const data = await response.json();
                    console.log(data);
                    // check if data message exist and if it's "not found"
                    if (data["message"] && data["message"] === "not found") {
                        Alert.error("City not found");
                    } else {
                        setWidgets(prevWidgets => {
                            return [{ value: data["name"], temperature: Math.floor((data["main"]["temp"] - 273.15)), description: data["weather"][0]["main"], humidity: data["main"]["humidity"], wind: data["wind"]["speed"] }, ...prevWidgets]
                        });
                        console.log(widgets);
                    }
                }
                getWeather(value);
                break;
            case "steam":
                const getSteam = async (arg) => {
                    const value = arg;
                    const getList = await fetch('http://localhost:8080/auth/services/steam/games');
                    const gameList = await getList.json();
                    console.log(gameList);
                    const gameId = gameList['applist']['apps'].filter(game => game.name === value)[0];
                    if (gameId == null) {
                        Alert.error("Game not found");
                    } else {
                        const response = await fetch('http://localhost:8080/auth/services/steam/players?value=' + gameId['appid']);
                        const steamData = await response.json();
                        console.log(steamData);
                        setWidgets(prevWidgets => {
                            return [{ value: value, playerCount: steamData["response"]["player_count"] }, ...prevWidgets]
                        });
                        console.log(widgets);
                    }

                }
                getSteam(value);
                break;
            case "deezer":
                const getDeezer = async (arg) => {
                    const value = arg;
                    const response = await fetch('http://localhost:8080/auth/services/music/artist?value=' + value);
                    const data = await response.json();
                    if (data["total"] === 0) {
                        Alert.error("Artist or song not found");
                    } else {
                        const coverAlbum = data["data"][0]["album"]["cover"];
                        const albumName = data["data"][0]["album"]["title"];
                        const songLink = data["data"][0]["preview"];
                        const artistName = data["data"][0]["artist"]["name"];
                        const songTitle = data["data"][0]["title"];
                        const deezerData = [coverAlbum, albumName, songLink, artistName, songTitle]
                        console.log(deezerData);
                        setWidgets(prevWidgets => {
                            return [{ value: value, coverAlbum: coverAlbum, albumName: albumName, artistName: artistName, songTitle: songTitle }, ...prevWidgets]
                        });
                        console.log(widgets);
                    }
                }
                getDeezer(value);
                break;
            case "ringtone":
                const getringtone = async (arg) => {
                    const value = arg;
                    const response = await fetch('http://localhost:8080/auth/services/music/artist?value=' + value);
                    const data = await response.json();
                    const coverAlbum = data["data"][0]["album"]["cover"];
                    const albumName = data["data"][0]["album"]["title"];
                    const songLink = data["data"][0]["preview"];
                    const artistName = data["data"][0]["artist"]["name"];
                    const songTitle = data["data"][0]["title"];
                    const deezerData = [coverAlbum, albumName, songLink, artistName, songTitle]
                    console.log(deezerData);
                    setWidgets(prevWidgets => {
                        return [{ songTitle: songTitle, songLink: songLink }, ...prevWidgets]
                    });
                    console.log(widgets);
                }
                getringtone(value);
                break;
            case "news":
                const getNews = async (arg) => {
                    const value = arg;
                    const response = await fetch('http://localhost:8080/auth/services/news/keyword?value=' + value);
                    const newsData = await response.json();
                    console.log(newsData);
                    for (let i = 0; i < range; i++) {
                        setWidgets(prevWidgets => {
                            return [{ value: newsData["articles"][i].title, title: newsData["articles"][i].title, source: newsData["articles"][i].source.name, author: newsData["articles"][i].author, resume: newsData["articles"][i].description, url: newsData["articles"][i].url, publishedAt: newsData["articles"][i].publishedAt }, ...prevWidgets]
                        });
                    }
                    console.log(widgets);

                }

                getNews(value);
                break;
            case "exchange":
                const getExchange = async (arg) => {
                    const value = arg;
                    const response = await fetch('http://localhost:8080/auth/services/exchange?value=' + value);
                    const exchangeData = await response.json();
                    // split the value string with the "/" separator to get the second currency
                    const currency = value.split("/");
                    console.log(currency);
                    if (exchangeData["message"] && exchangeData["message"] === "not found") {
                        Alert.error("Currency rate not found");
                    } else {
                        setWidgets(prevWidgets => {
                            return [{ value: value, currencyRate: exchangeData[currency[1]], firstCurrency: currency[0], secondCurrency: currency[1] }, ...prevWidgets]
                        });
                        console.log(widgets);
                    }
                }
                getExchange(value);
                break;
            case "timezone":
                const getTimezone = async (arg) => {
                    const value = arg;
                    const response = await fetch('http://localhost:8080/auth/services/timeZone?value=' + value);
                    const timezoneData = await response.json();
                    if (timezoneData["message"] && timezoneData["message"] === "not found") {
                        Alert.error("Timezone not found");
                    }
                    else {
                        setWidgets(prevWidgets => {
                            return [{ value: value, timeZone: timezoneData["timeZone"], day: timezoneData["dayOfWeek"], date: timezoneData["date"], time: timezoneData["time"] }, ...prevWidgets]
                        });
                        console.log(widgets);
                    }
                }
                getTimezone(value);
                break;
            default:
                return;
        }
    }

    const [services, setServices] = useState([{
        value: null,
        title: null,
        label: null,
        labelFor: null,
        onSubmit: null,
        inputName: null,
        installed: false
    }])

    const [widgets, setWidgets] = useState([{
        value: null,
        temperature: null,
        description: null,
        humidity: null,
        wind: null,
        coverAlbum: null,
        albumName: null,
        songLink: null,
        artistName: null,
        songTitle: null,
        title: null,
        source: null,
        author: null,
        resume: null,
        url: null,
        publishedAt: null,
        channelTitle: null,
        videoTitle: null,
        miniature: null,
        videoDescription: null,
        playerCount: null,
        currencyRate: null,
        firstCurrency: null,
        secondCurrency: null,
        timeZone: null,
        day: null,
        date: null,
        time: null,
    }])
    console.log(widgets);

    useEffect(() => {
        const storedServices = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
        if (storedServices) {
            setServices(storedServices)
        }
    }, [])

    useEffect(() => {
        localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(services))
    }, [services])

    const serviceNameRef = useRef()

    const handleAddService = () => {
        const name = serviceNameRef.current.value;
        const arrayServices = Object.values(services);
        if (name === '') {
            return;
        }
        for (let i = 0; i < arrayServices.length; i++) {
            if (arrayServices[i].value === name) {
                return;
            }
        }

        switch (name) {
            case "weather":
                setServices(prevServices => {
                    return [...prevServices, { value: name, title: "Weather report", label: "Choose a city", labelFor: "city", onSubmit: getJSON, inputName: "city", installed: true }]
                })
                break;
            case "deezer":
                setServices(prevServices => {
                    return [...prevServices, { value: name, title: "Deezer", label: "Choose a song", labelFor: "song", onSubmit: getJSON, inputName: "song", installed: true }]
                })
                break;
            case "ringtone":
                setServices(prevServices => {
                    return [...prevServices, { value: name, title: "Ringtone", label: "Choose a song", labelFor: "song", onSubmit: getJSON, inputName: "song", installed: true }]
                })
                break;
            case "news":
                setServices(prevServices => {
                    return [...prevServices, { value: name, title: "News", label: "Choose a news", labelFor: "news", onSubmit: getJSON, inputName: "news", installed: true }, { value: name, title: "News area", label: "Choose a news area", labelFor: "domain", onSubmit: getJSON, inputName: "domain", installed: true }]
                })
                break;
            case "steam":
                setServices(prevServices => {
                    return [...prevServices, { value: name, title: "Steam", label: "Choose a game", labelFor: "steam", onSubmit: getJSON, inputName: "steam", installed: true }]
                })
                break;
            case "youtube":
                setServices(prevServices => {
                    return [...prevServices, { value: name, title: "Youtube", label: "Choose a youtuber", labelFor: "youtube", onSubmit: getJSON, inputName: "youtube", installed: true }]
                })
                break;
            case "exchange":
                setServices(prevServices => {
                    return [...prevServices, { value: name, title: "Exchange rate", label: "Choose a currency conversion format: cur1/cur2", labelFor: "exchange", onSubmit: getJSON, inputName: "exchange", installed: true }]
                })
                break;
            case "timezone":
                setServices(prevServices => {
                    return [...prevServices, { value: name, title: "Timezone", label: "Choose a timezone", labelFor: "timezone", onSubmit: getJSON, inputName: "timezone", installed: true }]
                })
                break;
            default:
                return;
        }
    }

    const removeWidgets = (widgetValue) => {
        const wiValue = widgetValue;
        function filterWidgets(obj) {
            if (obj.value !== wiValue) {
                return true;
            }
        }
        const newWidgets = widgets.filter(filterWidgets);
        setWidgets(newWidgets);

        async function fetchDeleteWidgets() {
            console.log(wiValue);
            const response = await fetch("http://localhost:8080/auth/delWidgets", {
                method: "POST",
                headers: { "Content-Type": 'application/json' },
                mode: 'cors',
                body: JSON.stringify(wiValue)
            });
            const data = await response.json();
        }
        fetchDeleteWidgets()
    }

    const handleRemoveService = () => {
        const name = serviceNameRef.current.value;
        if (name === '') return;
        function filterRemove(obj) {
            if (obj.value !== name) {
                return true;
            }
        }
        const newServices = services.filter(filterRemove);
        setServices(newServices);
    }

    return (
        <div>
            <Row>
                <Col className="sidecolumn" >
                    <Container className="servicesSelect">
                        <h1>Subscribe to your services</h1>
                        <Form>
                            <Form.Select ref={serviceNameRef} id='servicesSelect' name="services">
                                {options.map((option) =>
                                    <option key={option.label} value={option.value}>{option.label}</option>
                                )}
                            </Form.Select><br />
                            <Button onClick={handleAddService} id='servicesAdd' type="button" className="btn btn-success addServButton" >Add</Button>
                            <Button onClick={handleRemoveService} id='servicesRemove' type="button" className="btn btn-danger">Remove</Button>
                        </Form>
                    </Container>
                </Col>
                <Col>
                    <Container>
                        <ServicesList widgets={widgets} getJSON={getJSON} services={services} />
                    </Container>
                    <Container>
                        <WidgetsList removeWidgets={removeWidgets} widgets={widgets} />
                    </Container>
                </Col>
            </Row>
        </div>
    )
}



export default Dashboard