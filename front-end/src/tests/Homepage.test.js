import Homepage from "../pages/Homepage";
import App from "../App";

const mockExercises = [
    { id: 1, exerciseGroup: "CHEST" },
    { id: 2, exerciseGroup: "BICEPS" },
  ];

  describe("Homepage", () => {
    test("defaults to front angle", async () => {
        render(
            <Homepage />)
a
        expect(angle).toEqual("front");
    })
    
})