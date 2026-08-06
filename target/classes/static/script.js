
// DOM Elements
const cityInput = document.getElementById('cityInput');
const citiesList = document.getElementById('citiesList');
const citiesUl = document.getElementById('citiesUl');
const citiesLoading = document.getElementById('citiesLoading');
const citiesError = document.getElementById('citiesError');
const weatherContent = document.getElementById('weatherContent');
const selectedCity = document.getElementById('selectedCity');
const selectedCityName = document.getElementById('selectedCityName');
const fetchWeatherBtn = document.getElementById('fetchWeatherBtn');

// State
let selectedCityData = null;
let searchTimeout = null;

// Event Listeners
cityInput.addEventListener('input', handleCityInput);
fetchWeatherBtn.addEventListener('click', handleFetchWeather);

/**
 * Handle city input - fetch cities as user types
 */
function handleCityInput(e) {
    const query = e.target.value.trim();


    if (searchTimeout) clearTimeout(searchTimeout);


    if (!query) {
        citiesList.classList.add('hidden');
        selectedCity.classList.add('hidden');
        selectedCityData = null;
        resetBackground();
        return;
    }


    citiesLoading.classList.remove('hidden');
    citiesError.classList.add('hidden');
    citiesList.classList.remove('hidden');


    searchTimeout = setTimeout(() => {
        fetchCities(query);
    }, 300);
}


async function fetchCities(city) {
    try {
        const response = await fetch(`/location/cities/${encodeURIComponent(city)}`);

        if (!response.ok) {
            throw new Error('City not found');
        }

        const cities = await response.json();


        citiesUl.innerHTML = '';


        cities.forEach((city) => {
            const li = document.createElement('li');
            li.innerHTML = `
                <span class="city-item-name">${city.name}</span>
                <span class="city-item-state">${city.state || 'Unknown'}</span>
            `;
            li.addEventListener('click', () => selectCity(city));
            citiesUl.appendChild(li);
        });

        citiesLoading.classList.add('hidden');
    } catch (error) {
        citiesUl.innerHTML = '';
        citiesLoading.classList.add('hidden');
        citiesError.classList.remove('hidden');
        citiesError.textContent = `❌ ${error.message}`;
    }
}


function selectCity(city) {
    selectedCityData = city;


    const stateName = city.state ? ` - ${city.state}` : '';
    selectedCityName.textContent = `${city.name}${stateName}`;
    selectedCity.classList.remove('hidden');
    citiesList.classList.add('hidden');


    cityInput.value = '';
}


async function handleFetchWeather() {
    if (!selectedCityData) return;

    fetchWeatherBtn.disabled = true;
    fetchWeatherBtn.textContent = 'Loading...';

    try {
        // Extract state from selectedCityData.state
        // Normalize state name: "São Paulo" -> "sao-paulo"
        const stateName = normalizeState(selectedCityData.state);

        const response = await fetch(
            `/current/${encodeURIComponent(stateName)}/${encodeURIComponent(selectedCityData.name)}`
        );

        if (!response.ok) {
            throw new Error('Unable to fetch weather data');
        }

        const weatherData = await response.json();
        displayWeather(weatherData);
        selectedCity.classList.add('hidden');
    } catch (error) {
        displayWeatherError(error.message);
    } finally {
        fetchWeatherBtn.disabled = false;
        fetchWeatherBtn.textContent = 'Get Weather';
    }
}


function normalizeState(state) {
    if (!state) return '';

    return state
        .normalize('NFD')                       // Decompose accents
        .replace(/[\u0300-\u036f]/g, '')       // Remove combining marks
        .toLowerCase()                          // Lowercase
        .replace(/\s+/g, '-');                  // Replace spaces with hyphens
}


function displayWeather(data) {
    const html = `
        <div class="weather-card">
            <div class="city-name">${selectedCityData.name}</div>
            <div class="state-name">${selectedCityData.state || 'Unknown'}</div>

            <div class="weather-main">
                <div class="temp-main">
                    <span class="temp-value">${Math.round(data.temp)}</span>
                    <span class="temp-unit">°C</span>
                </div>
            </div>

            <div class="feels-like">
                <span class="feels-like-label">Feels Like</span>
                <span class="feels-like-value">${Math.round(data.feels_like)}°C</span>
            </div>

            <div style="margin-top: 20px; font-size: 14px; text-align: center; opacity: 0.9;">
                <div><strong>Humidity:</strong> ${data.humidity || 'N/A'}%</div>
                <div><strong>Pressure:</strong> ${data.pressure || 'N/A'} hPa</div>
            </div>
        </div>
    `;

    weatherContent.innerHTML = html;


    updateBackgroundByTemperature(data.temp);
}

function updateBackgroundByTemperature(temp) {
    // Remove all temperature classes
    document.body.classList.remove(
        'weather-hot',
        'weather-warm',
        'weather-mild',
        'weather-cool',
        'weather-cold',
        'weather-freezing'
    );

    // Add appropriate class based on temperature
    if (temp >= 30) {
        document.body.classList.add('weather-hot');        // Red/Orange
    } else if (temp >= 20) {
        document.body.classList.add('weather-warm');       // Orange/Red
    } else if (temp >= 15) {
        document.body.classList.add('weather-mild');       // Purple (default)
    } else if (temp >= 5) {
        document.body.classList.add('weather-cool');       // Blue
    } else if (temp >= 0) {
        document.body.classList.add('weather-cold');       // Dark Blue
    } else {
        document.body.classList.add('weather-freezing');   // Deep Blue/Dark
    }
}


function resetBackground() {
    document.body.classList.remove(
        'weather-hot',
        'weather-warm',
        'weather-mild',
        'weather-cool',
        'weather-cold',
        'weather-freezing'
    );
}


function displayWeatherError(message) {
    const html = `
        <div class="weather-card weather-error">
            <div style="font-size: 40px; margin-bottom: 20px;">❌</div>
            <div style="font-size: 16px; font-weight: bold; margin-bottom: 10px;">Oops!</div>
            <div style="font-size: 14px;">${message}</div>
            <div style="margin-top: 20px; font-size: 12px; opacity: 0.9;">
                Please check if the state name is correct and try again.
            </div>
        </div>
    `;

    weatherContent.innerHTML = html;
}


window.addEventListener('load', () => {

    ['/imgs/hotTemp.jpg', '/imgs/avaregeTemp.jpg', '/imgs/coldTemp.jpg'].forEach(src => {
        const img = new Image();
        img.src = src;
    });

    cityInput.focus();
});

