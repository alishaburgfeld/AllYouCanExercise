import ExerciseGroupPage from "../pages/ExerciseGroupPage";
import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Routes, Route } from "react-router-dom";
import axios from "axios";

jest.mock("axios");

const mockedNavigate = jest.fn();

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => mockedNavigate,
}));

const mockExercises = [
  { id: 1, name: "Barbell Bench Press", exerciseGroup: "CHEST" },
  { id: 2, name: "Chest Press Machine", exerciseGroup: "CHEST" },
  { id: 3, name: "Reverse Barbell Curl", exerciseGroup: "BICEPS" },
];

describe("ExerciseGroup Page", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  // Helper function to render component with router context
  const renderWithRouter = (path = "/exercises/chest") => {
    // Part 1: Set up browser history
    window.history.pushState({}, "", path);
    return render(
      // The MemoryRouter creates a virtual routing environment that doesn't affect the real browser's URL. It's like a sandbox where we can test routing behavior without actually changing pages. The initialEntries prop tells it where to start - in our case, whatever path we passed to the helper function.
      <MemoryRouter initialEntries={[path]}>
        <Routes>
          {/* /:group is a dynamic parameter that matches any value after the slash
            When we navigate to '/biceps', ':group' becomes 'biceps' */}
          <Route
            path="/exercises/:exerciseGroup"
            element={<ExerciseGroupPage />}
          />
        </Routes>
      </MemoryRouter>,
    );
  };

  test("renders exercises when API call succeeds", async () => {
    axios.get.mockResolvedValue({ data: mockExercises });

    renderWithRouter("/chest");

    expect(axios.get).toHaveBeenCalledWith(
      expect.stringContaining("/api/exercises/group/chest"),
    );
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(
      screen.getAllByText(/Barbell Bench Press/i).toBeInTheDocument(),
    );
  });
});
