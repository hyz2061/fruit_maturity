export const hasPermission = (code: string, permissions: string[]) => {
  return permissions.includes(code);
};
