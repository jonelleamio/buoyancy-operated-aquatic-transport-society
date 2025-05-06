/// <reference types="vitest" />

import angular from '@analogjs/vite-plugin-angular';

import { defineConfig } from 'vite';
import { configDefaults } from 'vitest/config';

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  return {
    plugins: [
      angular(),

    ],
    test: {
      reporters: ['verbose', 'vitest-sonar-reporter'],
      outputFile: {
        'vitest-sonar-reporter': 'target/test-results/TESTS-results-sonar.xml',
      },
      globals: true,
      logHeapUsage: true,
      poolOptions: {
        threads: {
          minThreads: 1,
          maxThreads: 2,
        },
      },
      environment: 'node',
      cache: false,
      setupFiles: ['src/test/webapp/unit/vitest-setup.ts'],
      include: ['src/test/webapp/unit/**/*.{test,spec}.?(c|m)[jt]s?(x)'],
      coverage: {
        thresholds: {
          perFile: true,
          autoUpdate: true,
          100: true,
        },
        include: ['src/main/webapp/**/*.ts?(x)'],
        exclude: [...(configDefaults.coverage.exclude as string[])],
        provider: 'istanbul',
        reportsDirectory: 'target/test-results/',
        reporter: ['html', 'json-summary', 'text', 'text-summary', 'lcov', 'clover'],
        watermarks: {
          statements: [100, 100],
          branches: [100, 100],
          functions: [100, 100],
          lines: [100, 100],
        },
      },
    },
    define: {
      'import.meta.vitest': mode !== 'production',
    },
  };
});
