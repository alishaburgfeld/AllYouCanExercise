// import { HashRouter } from 'react-router-dom';
// import { render, screen } from '@testing-library/react';
// import App from '../App';

// test('renders Navbar text', () => {
//   render(
//       <App />
//   );
//   const navbarText = screen.getByText(/Navbar here/i);
//   expect(navbarText).toBeInTheDocument();
// });


import { render, screen, waitFor } from "@testing-library/react";
import App from "../App.js"
import axios from "axios";
import { BrowserRouter } from "react-router-dom";

jest.mock("axios");

// Mock exercises data
const mockExercises = [
  { id: 1, exerciseGroup: "CHEST" },
  { id: 2, exerciseGroup: "BICEPS" },
];

describe("App Component", () => {
  test("renders Navbar and Routes", async () => {
    axios.get.mockResolvedValueOnce({ data: mockExercises });

    render(
    <BrowserRouter>
    <App />
    </BrowserRouter>)

    await waitFor(() => expect(axios.get).toHaveBeenCalled());

    expect(screen.getByRole("navigation")).toBeInTheDocument();
    expect(screen.getByText("Homepage")).toBeInTheDocument();
  });
});
