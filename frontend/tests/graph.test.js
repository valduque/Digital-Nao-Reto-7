/**
 * Tests for graph.js
 */

import {
  Graph,
  validateGraphData,
  buildGraph,
  getNearbyCities,
  prepareGraphData,
  sampleData
} from "../js/graph.js";

describe("Graph class", () => {
  test("adds cities correctly", () => {
    const g = new Graph();
    g.addCity("A");
    expect(g.adj.has("A")).toBe(true);
  });

  test("throws on invalid city name", () => {
    const g = new Graph();
    expect(() => g.addCity("")).toThrow();
    expect(() => g.addCity(null)).toThrow();
  });

  test("adds edges correctly", () => {
    const g = new Graph();
    g.addCity("A");
    g.addCity("B");
    g.addEdge("A", "B", 10);

    expect(g.neighbors("A")).toEqual([{ to: "B", distance: 10 }]);
    expect(g.neighbors("B")).toEqual([{ to: "A", distance: 10 }]);
  });

  test("throws when adding an edge with unknown city", () => {
    const g = new Graph();
    g.addCity("A");
    expect(() => g.addEdge("A", "X", 10)).toThrow("Unknown city");
  });

  test("throws on invalid distance", () => {
    const g = new Graph();
    g.addCity("A");
    g.addCity("B");
    expect(() => g.addEdge("A", "B", -5)).toThrow("Invalid distance");
  });

  test("neighbors() throws on unknown city", () => {
    const g = new Graph();
    expect(() => g.neighbors("X")).toThrow("Unknown city");
  });
});

describe("validateGraphData()", () => {
  test("passes for valid dataset", () => {
    const result = validateGraphData(sampleData);
    expect(result.ok).toBe(true);
  });

  test("fails if cities or edges are not arrays", () => {
    expect(validateGraphData({ cities: {}, edges: [] }).ok).toBe(false);
  });

  test("fails on duplicate cities", () => {
    const data = { cities: ["A", "A"], edges: [] };
    expect(validateGraphData(data).ok).toBe(false);
  });

  test("fails if a city is invalid", () => {
    const data = { cities: ["A", ""], edges: [] };
    expect(validateGraphData(data).ok).toBe(false);
  });

  test("fails if an edge references unknown city", () => {
    const data = {
      cities: ["A", "B"],
      edges: [{ from: "A", to: "C", distance: 5 }]
    };
    expect(validateGraphData(data).ok).toBe(false);
  });

  test("fails on invalid distances", () => {
    const data = {
      cities: ["A", "B"],
      edges: [{ from: "A", to: "B", distance: -2 }]
    };
    expect(validateGraphData(data).ok).toBe(false);
  });
});

describe("buildGraph()", () => {
  test("creates graph with correct cities and edges", () => {
    const g = buildGraph(sampleData.cities, sampleData.edges);
    expect(g.neighbors("Guadalajara").length).toBe(4);
  });
});

describe("getNearbyCities()", () => {
  const g = buildGraph(sampleData.cities, sampleData.edges);

  test("returns nearby cities sorted by distance", () => {
    const result = getNearbyCities(g, "Guadalajara", 100);
    expect(result.map(r => r.city)).toEqual([
      "Tlaquepaque",
      "Zapopan",
      "Tequila",
      "Tepatitlán"
    ]);
  });

  test("returns empty array for invalid destination", () => {
    expect(getNearbyCities(g, "Unknown")).toEqual([]);
  });

  test("filters by maxDistanceKm", () => {
    const result = getNearbyCities(g, "Guadalajara", 20);
    expect(result).toEqual([
      { city: "Tlaquepaque", distance: 10 },
      { city: "Zapopan", distance: 12 }
    ]);
  });

  test("throws if graph argument is invalid", () => {
    expect(() => getNearbyCities({}, "A")).toThrow();
  });
});

describe("prepareGraphData()", () => {
  test("returns a new array when valid", () => {
    const data = [1, 2, 3];
    const result = prepareGraphData(data);
    expect(result).not.toBe(data);
    expect(result).toEqual([1, 2, 3]);
  });

  test("throws for invalid data", () => {
    expect(() => prepareGraphData(null)).toThrow("Invalid data");
    expect(() => prepareGraphData({})).toThrow("Invalid data");
  });
});
