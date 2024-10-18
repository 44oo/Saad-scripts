    Bypass = true

    local GameMT = getrawmetatable(game)
    -- Backup variables
    local OldIndexFunc = GameMT.__index;
    local OldNamecallFunc = GameMT.__namecall
    setreadonly(GameMT, false)
    if (Bypass == true) then
        -- This will be so laggy
        GameMT.__namecall = newcclosure(function(self, ...) 
            local NamecallArgs = {...}
    
            local DETECTION_STRINGS = 
            {
                'CHECKER_1';
                'CHECKER';
                'OneMoreTime';
                'checkingSPEED';
                'PERMAIDBAN';
                'BANREMOTE';
                'FORCEFIELD';
                'TeleportDetect';
            };
    
            if (table.find(DETECTION_STRINGS, NamecallArgs[1]) and getnamecallmethod() == 'FireServer') then 
                return;
            end;
            local suc, err = pcall(getfenv, 2)
            if not (err) then 
                if getfenv(2).crash then 
                    hookfunction(getfenv(2).crash, function() 
                        
                    end)
                end
            end
            return OldNamecallFunc(self, ...)
        end)
    end
