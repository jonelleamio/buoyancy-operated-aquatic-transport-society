export type Link = {
  href: string;
  deprecation?: string;
  hreflang?: string;
  name?: string;
  profile?: string;
  templated?: boolean;
  title?: string;
  type?: string;
}

export type HalResource<T> = T & {
  _links: Record<string, Link>;
  _embedded?: Record<string, unknown>;
};
