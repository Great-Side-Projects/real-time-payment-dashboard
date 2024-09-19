export const getRandomCountryCode = () => {
    const countryCodes = ['US', 'CA', 'GB', 'FR', 'DE', 'JP', 'AU', 'BR', 'IN', 'CN', 'KR', 'RU', 'SA', 'ZA', 'MX', 'ES', 'IT', 'NL', 'SE', 'CH', 'SG', 'AE', 'HK', 'TW', 'ID', 'TH', 'MY', 'PH', 'VN', 'PL', 'TR', 'AR', 'CL', 'CO', 'PE', 'IL', 'EG', 'NG', 'KE', 'MA', 'DZ', 'PT', 'AT', 'BE', 'DK', 'FI', 'IE', 'NO', 'CZ', 'GR', 'HU', 'RO', 'SK', 'UA', 'HR', 'RS', 'BG', 'SI', 'LT', 'LV', 'EE', 'IS', 'LU', 'MT', 'CY']
    return countryCodes[Math.floor(Math.random() * countryCodes.length)]
  }
  
  export const UserIds = ['U1', 'U2', 'U3', 'U4', 'U5', 'U6', 'U7', 'U8', 'U9', 'U10', 'U11', 'U12', 'U13', 'U14', 'U15', 'U16', 'U17', 'U18', 'U19', 'U20', 'U21', 'U22', 'U23', 'U24', 'U25', 'U26', 'U27', 'U28', 'U29', 'U30']   

  export const getRandomUserid = () => {
    return UserIds[Math.floor(Math.random() * UserIds.length)]
  }