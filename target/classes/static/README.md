# WeatherAPI Frontend

A simple, modern web interface for the WeatherAPI backend.

## Features

✨ **Real-time City Search** - Type a city name and see available options with their states
🌡️ **Weather Display** - View current temperature, "feels like", humidity, and pressure
🎨 **Clean UI** - Modern split-panel design with gradient styling
📱 **Responsive Design** - Works on desktop and mobile

## How It Works

1. **Type a city name** in the search bar on the right
2. **Select a city** from the dropdown results
3. **Click "Get Weather"** to fetch and display current weather
4. **Weather info** appears on the left side with temperature and details

## File Structure

```
src/main/resources/static/
├── index.html      # Main HTML page
├── style.css       # Styling (gradients, layout, responsive)
└── script.js       # API integration and interactions
```

## Setup & Running

1. **Start your Spring Boot application:**
   ```bash
   mvn spring-boot:run
   ```

2. **Open in browser:**
   ```
   http://localhost:8080
   ```

The frontend will be automatically served by Spring Boot from the static folder!

## Important Notes

- **Redis & API Key Required**: Make sure your backend has Redis running and the `weatherkey` environment variable set
- **State Name Format**: The app automatically normalizes state names (removes accents, converts to lowercase with hyphens)
  - Example: "São Paulo" → "sao-paulo"
- **Debounced Search**: City search has a 300ms debounce to avoid excessive API calls

## Troubleshooting

**"City not found" error?**
- Make sure the city name is spelled correctly
- Check if the OpenWeatherMap API has results for that city

**Weather fetch fails?**
- Verify the state name is correct (use the dropdown to see available states)
- Check Redis and API key are configured properly

**CORS errors?**
- The backend already has `@CrossOrigin()` enabled on all endpoints
- If you still get errors, verify your Spring Boot is running on `http://localhost:8080`

